package dev.m7med.economysystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {
    private final Connection connection;
    private final FileConfiguration config = EconomySystem.getInstance().getConfig();
    private final String systemName = config.getString("SystemName");
    private final String currencyName = config.getString("CurrencyName");
    private final String currencyNamePlural = config.getString("CurrencyNamePlural");
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
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "uuid TEXT PRIMARY KEY," +
                    "balance REAL DEFAULT 0," +
                    "username TEXT NOT NULL)");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection", e);
        }
    }

    public boolean hasAccount(UUID uuid) {
        String sql = "SELECT 1 FROM players WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check if account exists", e);
        }
    }

    public void createAccount(UUID uuid, String name) {
        if (hasAccount(uuid)) return;

        String sql = "INSERT INTO players (uuid, balance, username) VALUES (?, 0, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create account", e);
        }
    }

    public double getBalance(UUID uuid) {
        String sql = "SELECT balance FROM players WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getDouble("balance");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get balance", e);
        }
    }

    public boolean setBalance(UUID uuid, double amount) {
        if (amount < 0) return false;

        String sql = "UPDATE players SET balance = ? WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setString(2, uuid.toString());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set balance", e);
        }
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

        String sql = "SELECT username, balance FROM players ORDER BY balance DESC LIMIT ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);

            try (ResultSet result = statement.executeQuery()) {
                int rank = 1;
                while (result.next()) {
                    String username = result.getString("username");
                    double balance = result.getDouble("balance");

                    String line = rawFormat
                            .replace("%player%", username)
                            .replace("%amount%", formatAmount(balance))
                            .replace("<rank>", String.valueOf(rank));

                    topList.add(MiniMessage.miniMessage().deserialize(line));

                    rank++;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch top balances", e);
        }

        return topList;
    }

}