package dev.m7med.economysystem.commands;

import dev.m7med.economysystem.EconomyManager;
import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.Command;
import dev.velix.imperat.annotations.Dependency;
import dev.velix.imperat.annotations.Permission;
import dev.velix.imperat.annotations.SubCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Command("aeco")
@Permission("eco.admincommands")
public class AdminCommands {
    @Dependency
    public EconomyManager economyManager;
    @SubCommand("give")
    public void give(BukkitSource source, Player player, double amount) {
        economyManager.deposit(player.getUniqueId(), amount);

        Map<String, String> senderPlaceholders = new HashMap<>();
        senderPlaceholders.put("amount", String.valueOf(amount));
        senderPlaceholders.put("currency", economyManager.getCurrencyNamePlural());
        senderPlaceholders.put("player", player.getName());

        Map<String, String> receiverPlaceholders = new HashMap<>();
        receiverPlaceholders.put("amount", String.valueOf(amount));
        receiverPlaceholders.put("currency", economyManager.getCurrencyNamePlural());
        receiverPlaceholders.put("player", source.asPlayer().getName());

        source.reply(economyManager.get(
                "Give-Success",
                senderPlaceholders,
                "<green>You added <yellow>%amount% %currency% <green>to <aqua>%player%'s balance"
        ));

        player.sendMessage(economyManager.get(
                "Give-Receiver",
                receiverPlaceholders,
                "<green>Your balance has been increased by <yellow>%amount% %currency%"
        ));
    }
    public void take(BukkitSource source, Player player, double amount) {
        double currentAmount = economyManager.getBalance(player.getUniqueId());

        if (currentAmount < amount) {
            Map<String, String> errorPlaceholders = new HashMap<>();
            errorPlaceholders.put("amount", String.valueOf(amount));
            errorPlaceholders.put("currency", economyManager.getCurrencyNamePlural());
            errorPlaceholders.put("player", player.getName());

            source.reply(economyManager.get(
                    "Error-Take-Insufficient-Funds",
                    errorPlaceholders,
                    "<red>%player% doesn't have enough %currency% to take!"
            ));
            return;
        }

        economyManager.withdraw(player.getUniqueId(), amount);

        Map<String, String> senderPlaceholders = new HashMap<>();
        senderPlaceholders.put("amount", String.valueOf(amount));
        senderPlaceholders.put("currency", economyManager.getCurrencyNamePlural());
        senderPlaceholders.put("player", player.getName());

        Map<String, String> receiverPlaceholders = new HashMap<>();
        receiverPlaceholders.put("amount", String.valueOf(amount));
        receiverPlaceholders.put("currency", economyManager.getCurrencyNamePlural());

        source.reply(economyManager.get(
                "Take-Success",
                senderPlaceholders,
                "<green>You removed <yellow>%amount% %currency% <green>from <aqua>%player%'s balance"
        ));

        player.sendMessage(economyManager.get(
                "Take-Receiver",
                receiverPlaceholders,
                "<red><aqua>%amount% %currency% <red>was taken from your balance"
        ));
    }
    @SubCommand("set")
    public void set(BukkitSource source, Player player, double amount) {
        economyManager.setBalance(player.getUniqueId(), amount);

        Map<String, String> senderPlaceholders = new HashMap<>();
        senderPlaceholders.put("amount", String.valueOf(amount));
        senderPlaceholders.put("currency", economyManager.getCurrencyNamePlural());
        senderPlaceholders.put("player", player.getName());

        Map<String, String> receiverPlaceholders = new HashMap<>();
        receiverPlaceholders.put("amount", String.valueOf(amount));
        receiverPlaceholders.put("currency", economyManager.getCurrencyNamePlural());

        source.reply(economyManager.get(
                "Set-Success",
                senderPlaceholders,
                "<green>You set <aqua>%player%'s <green>balance to <yellow>%amount% %currency%"
        ));

        player.sendMessage(economyManager.get(
                "Set-Receiver",
                receiverPlaceholders,
                "<yellow>Your balance was set to <yellow>%amount% %currency%"
        ));
    }
    @SubCommand("reset")
    public void reset(BukkitSource source, Player player) {
        economyManager.setBalance(player.getUniqueId(), 0);

        Map<String, String> senderPlaceholders = new HashMap<>();
        senderPlaceholders.put("currency", economyManager.getCurrencyNamePlural());
        senderPlaceholders.put("player", player.getName());

        Map<String, String> receiverPlaceholders = new HashMap<>();
        receiverPlaceholders.put("currency", economyManager.getCurrencyNamePlural());

        source.reply(economyManager.get(
                "Reset-Success",
                senderPlaceholders,
                "<green>You reset <aqua>%player%'s <green>balance to <yellow>0 %currency%"
        ));

        player.sendMessage(economyManager.get(
                "Reset-Receiver",
                receiverPlaceholders,
                "<red>Your balance has been reset to <yellow>0 %currency%"
        ));
    }



}
