package dev.m7med.economysystem.commands;

import dev.m7med.economysystem.EconomyManager;
import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Command("pay")
@Description("Send money to another player")
@Permission("eco.pay")
public class PayCommands {
    @Dependency
    private EconomyManager economyManager;
    @Usage
    public void Pay(BukkitSource source, Player player, double amount) {
        if(source.isConsole()) {source.reply(economyManager.get("Error-Pay-Console","<red>Console can't use pay"));return;};
        if(source.asPlayer().getUniqueId().equals(player.getUniqueId())) {
            source.reply(economyManager.get("Error-Pay-Onself","<red>You can't pay yourself!"));
            return;
        }
        if(amount <= 0) {
            source.reply(economyManager.get("Error-Pay-Invalid-Amount","<red>Please enter a valid amount!"));
            return;
        }
        economyManager.withdraw(source.asPlayer().getUniqueId(), amount);
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
                "Pay-Success-Sender",senderPlaceholders,"<green>You sent <yellow>%amount% %currency% <green>to <aqua>%player%"
        ));

        player.sendMessage(economyManager.get("Pay-Success-Receiver",receiverPlaceholders,"<green>You received <yellow>%amount% %currency% <green>from <aqua>%player%"));
    }

}
