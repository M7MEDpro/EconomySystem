package dev.m7med.economysystem;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class VaultAPI implements Economy {

    private final EconomyManager economyManager;
    private final EconomySystem plugin;

    public VaultAPI(EconomySystem plugin, EconomyManager economyManager) {
        this.plugin = plugin;
        this.economyManager = economyManager;
    }

    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public String getName() {
        return economyManager.getSystemName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    private UUID getUUIDFromName(String playerName) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        return player.getUniqueId();
    }

    @Override
    public String format(double v) {
        return economyManager.formatAmount(v);
    }

    @Override
    public String currencyNamePlural() {
        return economyManager.getCurrencyNamePlural();
    }

    @Override
    public String currencyNameSingular() {
        return economyManager.getCurrencyName();
    }

    @Override
    public boolean hasAccount(String s) {
        try {
            UUID uuid = getUUIDFromName(s);
            return economyManager.hasAccount(uuid);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return economyManager.hasAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        try {
            UUID uuid = getUUIDFromName(s);
            return economyManager.getBalance(uuid);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return economyManager.getBalance(offlinePlayer.getUniqueId());
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        try {
            UUID uuid = getUUIDFromName(s);
            return economyManager.has(uuid, v);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return economyManager.has(offlinePlayer.getUniqueId(), v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        try {
            UUID uuid = getUUIDFromName(s);

            if (v < 0) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative amounts");
            }

            if (!economyManager.hasAccount(uuid)) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player does not have an account");
            }

            double balance = economyManager.getBalance(uuid);
            if (!economyManager.has(uuid, v)) {
                return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
            }

            boolean success = economyManager.withdraw(uuid, v);
            if (success) {
                double newBalance = economyManager.getBalance(uuid);
                return new EconomyResponse(v, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
            } else {
                return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Failed to withdraw");
            }
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        try {
            UUID uuid = offlinePlayer.getUniqueId();

            if (v < 0) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative amounts");
            }

            if (!economyManager.hasAccount(uuid)) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player does not have an account");
            }

            double balance = economyManager.getBalance(uuid);
            if (!economyManager.has(uuid, v)) {
                return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
            }

            boolean success = economyManager.withdraw(uuid, v);
            if (success) {
                double newBalance = economyManager.getBalance(uuid);
                return new EconomyResponse(v, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
            } else {
                return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Failed to withdraw");
            }
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        try {
            UUID uuid = getUUIDFromName(playerName);

            if (amount < 0) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative amounts");
            }

            if (!economyManager.hasAccount(uuid)) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
                economyManager.createAccount(uuid, player.getName());
            }

            double balance = economyManager.getBalance(uuid);
            boolean success = economyManager.deposit(uuid, amount);

            if (success) {
                double newBalance = economyManager.getBalance(uuid);
                return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
            } else {
                return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Failed to deposit");
            }
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        try {
            UUID uuid = offlinePlayer.getUniqueId();

            if (amount < 0) {
                return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative amounts");
            }

            if (!economyManager.hasAccount(uuid)) {
                economyManager.createAccount(uuid, offlinePlayer.getName());
            }

            double balance = economyManager.getBalance(uuid);
            boolean success = economyManager.deposit(uuid, amount);

            if (success) {
                double newBalance = economyManager.getBalance(uuid);
                return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
            } else {
                return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Failed to deposit");
            }
        } catch (Exception e) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank support is not enabled");
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        try {
            UUID uuid = getUUIDFromName(s);
            if (economyManager.hasAccount(uuid)) {
                return false;
            }
            economyManager.createAccount(uuid, s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        try {
            UUID uuid = offlinePlayer.getUniqueId();
            if (economyManager.hasAccount(uuid)) {
                return false;
            }
            economyManager.createAccount(uuid, offlinePlayer.getName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer);
    }
}