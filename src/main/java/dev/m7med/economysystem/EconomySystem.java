package dev.m7med.economysystem;

import dev.m7med.economysystem.commands.AdminCommands;
import dev.m7med.economysystem.commands.BalTopCommand;
import dev.m7med.economysystem.commands.BalanceCommands;
import dev.m7med.economysystem.commands.PayCommands;
import dev.velix.imperat.BukkitImperat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomySystem extends JavaPlugin {
    private static  EconomySystem instance;
    private EconomyManager economyManager;
    private BukkitImperat imperat;
    private VaultAPI vaultAPI;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        economyManager = new EconomyManager(getDataFolder().getAbsolutePath()+"/economy.db");
        vaultAPI = new VaultAPI(instance, economyManager);
        imperat = BukkitImperat.builder(this).dependencyResolver(EconomyManager.class,()-> economyManager).build();
        imperat.registerCommand(new BalanceCommands());
        imperat.registerCommand(new BalTopCommand());
        imperat.registerCommand(new PayCommands());
        imperat.registerCommand(new AdminCommands());
        Bukkit.getPluginManager().registerEvents(new JoinListener(economyManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static EconomySystem getInstance() {
        return instance;
    }
}
