package dev.m7med.economysystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        economyManager.loadPlayer(event.getPlayer().getUniqueId(), player.getName());
    }
@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        economyManager.unloadPlayer(event.getPlayer().getUniqueId());
}


}
