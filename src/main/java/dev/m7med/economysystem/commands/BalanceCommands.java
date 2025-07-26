package dev.m7med.economysystem.commands;

import dev.m7med.economysystem.EconomyManager;
import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.*;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Command({"balance","bal"})
@Permission("eco.bal")
@Description("Use to get the your balance or other's balance")
public class BalanceCommands {
    @Dependency
    private EconomyManager economyManager;
    @Usage
    public void onSelf(BukkitSource sender) {
        if(sender.isConsole()) {sender.reply(economyManager.get("Error-Console-Balance-onself","<red>Console can't have a balance"));return;}
        double bal = economyManager.getBalance(sender.asPlayer().getUniqueId());
        Map<String,String> placeholders = new HashMap<>();
        placeholders.put("amount", String.valueOf(bal));
        Component msg = economyManager.get(" Balance-onself",placeholders,"<green>Your balance: <yellow>%amount%");
        sender.reply(msg);
    }
    @Usage

    public void onOther(BukkitSource sender, Player player) {
        double bal = economyManager.getBalance(player.getUniqueId());
        Map<String,String> placeholders = new HashMap<>();
        placeholders.put("amount", String.valueOf(bal));
        placeholders.put("player", player.getName());
        Component msg = economyManager.get("Balance-Other",placeholders,"<green>%player%'s balance: <yellow>%amount%");
        sender.reply(msg);
    }
}
