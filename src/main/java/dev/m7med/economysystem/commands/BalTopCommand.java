package dev.m7med.economysystem.commands;

import dev.m7med.economysystem.EconomyManager;
import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.Command;
import dev.velix.imperat.annotations.Dependency;
import dev.velix.imperat.annotations.Usage;
import net.kyori.adventure.text.Component;

import java.util.List;

@Command("baltop")
public class BalTopCommand {
    @Dependency
    private EconomyManager economyManager;
    @Usage

    public void useNoNumber(BukkitSource source) {
        List<Component> messages = economyManager.getTopBalances(5);
        for (Component msg : messages) {
            source.reply(msg);
        }
    }
    @Usage

    public void useWithNumber(BukkitSource source,int amount) {
        if(amount <= 0) {
            economyManager.get("Error-Pay-Invalid-Amount","<red>Please enter a valid amount!");
            return;}
        List<Component> messages = economyManager.getTopBalances(amount);
        for (Component msg : messages) {
            source.reply(msg);
        }
    }
}
