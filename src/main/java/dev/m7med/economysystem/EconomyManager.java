package dev.m7med.economysystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EconomyManager {

    private final Connection connection;
    private final FileConfiguration config;
    private final String systemName;
    private final String currencyName;
    private final int defaultBalance;
    private final int defaultTop;
    private final String currencyNamePlural;

    private final ConcurrentHashMap<UUID, Double> balanceCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> usernameCache = new ConcurrentHashMap<>();
    private static volatile boolean dataChanged = false;

    public static Component get(String key, Map<String, String> placeholders, String defaultMessage) {
        String raw = EconomySystem.getInstance().getConfig().getString("messages." + key, defaultMessage);

        for(Map.Entry<String, String> entry : placeholders.entrySet()) {
            raw = raw.replace("%" + (String)entry.getKey() + "%", (CharSequence)entry.getValue());
        }

        return MiniMessage.miniMessage().deserialize(raw);
    }

    public static Component get(String key, String defaultMessage) {
        String raw = EconomySystem.getInstance().getConfig().getString("messages." + key, defaultMessage);
        return MiniMessage.miniMessage().deserialize(raw);
    }
    public EconomyManager(String path) {
        try {
            config = EconomySystem.getInstance().getConfig();
            systemName = config.getString("SystemName");
            currencyName = config.getString("CurrencyName");
            currencyNamePlural = config.getString("CurrencyNamePlural");
            defaultBalance = config.getInt("DefaultBalance");
            defaultTop = config.getInt("DefaultTop");

            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "uuid TEXT PRIMARY KEY," +
                    "balance REAL DEFAULT 0," +
                    "username TEXT NOT NULL)");
            statement.close();

            startAutoSave();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public void loadPlayer(UUID uuid, String username) {
        Bukkit.getScheduler().runTaskAsynchronously(EconomySystem.getInstance(), () -> {
            String sql = "SELECT balance FROM players WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, uuid.toString());
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        double balance = result.getDouble("balance");
                        balanceCache.put(uuid, balance);
                        usernameCache.put(uuid, username);
                    } else {
                        balanceCache.put(uuid, (double) defaultBalance);
                        usernameCache.put(uuid, username);
                        dataChanged = true;
                    }
                }
            } catch (SQLException e) {
                EconomySystem.getInstance().getLogger().severe("Failed to load player: " + e.getMessage());
            }
        });
    }

    private void startAutoSave() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(EconomySystem.getInstance(), () -> {
            if (dataChanged) {
                saveAllToDatabase();
                dataChanged = false;
            }
        }, 1200L, 1200L);
    }
    public void unloadPlayer(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(EconomySystem.getInstance(), () -> {
            if (balanceCache.containsKey(uuid)) {
                String sql = "INSERT OR REPLACE INTO players (uuid, balance, username) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, uuid.toString());
                    statement.setDouble(2, balanceCache.get(uuid));
                    statement.setString(3, usernameCache.get(uuid));
                    statement.executeUpdate();
                } catch (SQLException e) {
                    EconomySystem.getInstance().getLogger().severe("Failed to save player data before unloading: " + e.getMessage());
                }

                balanceCache.remove(uuid);
                usernameCache.remove(uuid);
            }
        });
    }
    private void saveAllToDatabase() {
        String sql = "INSERT OR REPLACE INTO players (uuid, balance, username) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<UUID, Double> entry : balanceCache.entrySet()) {
                UUID uuid = entry.getKey();
                statement.setString(1, uuid.toString());
                statement.setDouble(2, entry.getValue());
                statement.setString(3, usernameCache.get(uuid));
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            EconomySystem.getInstance().getLogger().severe("Failed to save to database: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (dataChanged) {
            saveAllToDatabase();
        }
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection", e);
        }
    }

    public boolean hasAccount(UUID uuid) {
        return balanceCache.containsKey(uuid);
    }

    public void createAccount(UUID uuid, String name) {
        if (hasAccount(uuid)) return;

        balanceCache.put(uuid, (double) defaultBalance);
        usernameCache.put(uuid, name);
        dataChanged = true;
    }

    public int getDefaultBalance() {
        return defaultBalance;
    }

    public int getDefaultTop() {
        return defaultTop;
    }

    public double getBalance(UUID uuid) {
        return balanceCache.getOrDefault(uuid, 0.0);
    }

    public boolean setBalance(UUID uuid, double amount) {
        if (amount < 0) return false;

        balanceCache.put(uuid, amount);
        dataChanged = true;
        return true;
    }

    public boolean deposit(UUID uuid, double amount) {
        if (amount <= 0) return false;

        double balance = getBalance(uuid);
        return setBalance(uuid, balance + amount);
    }

    public boolean withdraw(UUID uuid, double amount) {
        if (amount <= 0 || !has(uuid, amount)) return false;

        double current = getBalance(uuid);
        return setBalance(uuid, current - amount);
    }

    public boolean has(UUID uuid, double amount) {
        return getBalance(uuid) >= amount;
    }

    public String formatAmount(double amount) {
        if (amount == 1.0) {
            return String.format("%.2f %s", amount, currencyName);
        } else {
            return String.format("%.2f %s", amount, currencyNamePlural);
        }
    }

    public String getSystemName() {
        return systemName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencyNamePlural() {
        return currencyNamePlural;
    }

    public List<Component> getTopBalances(int limit) {
        List<Component> topList = new ArrayList<>();
        String rawFormat = EconomySystem.getInstance().getConfig()
                .getString("messages.Top-Format", "<gray>#<rank> %player% has %amount%</gray>");

        balanceCache.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .forEach(entry -> {
                    int rank = topList.size() + 1;
                    UUID uuid = entry.getKey();
                    double balance = entry.getValue();
                    String username = usernameCache.get(uuid);

                    String line = rawFormat
                            .replace("%player%", username)
                            .replace("%amount%", formatAmount(balance))
                            .replace("<rank>", String.valueOf(rank));

                    topList.add(MiniMessage.miniMessage().deserialize(line));
                });

        return topList;
    }
}