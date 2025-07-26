package dev.m7med.economysystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final EconomyManager economyManager;

    public JoinListener(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!economyManager.hasAccount(player.getUniqueId())) {
            economyManager.createAccount(player.getUniqueId(), player.getName());
        }
    }



}
