package ru.anton_flame.afpotionsstacker;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.anton_flame.afpotionsstacker.commands.AFPotionsStackerCommand;
import ru.anton_flame.afpotionsstacker.listeners.EventListener;
import ru.anton_flame.afpotionsstacker.utils.ConfigManager;

public final class AFPotionsStacker extends JavaPlugin {

    public LuckPerms luckPerms;
    public PlayerPointsAPI playerPointsAPI;
    public Economy vaultAPI;

    @Override
    public void onEnable() {
        getLogger().info("Плагин был включен!");
        saveDefaultConfig();
        ConfigManager.setupConfigValues(this);

        if (!setupEconomy()) {
            getLogger().severe("Ни один плагин на экономику не был найден. Плагин будет выключен!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        getCommand("afpotionsstacker").setExecutor(new AFPotionsStackerCommand(this));

        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            luckPerms = LuckPermsProvider.get();
            getLogger().info("Плагин LuckPerms найден! Лимиты для групп будут работать!");
        } else {
            getLogger().warning("Плагин LuckPerms не найден! Лимиты для групп не будут работать!");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Плагин был выключен!");
    }

    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        vaultAPI = rsp.getProvider();
        return true;
    }

    private boolean setupEconomy() {
        boolean isPlayerPointsEnabled = false;
        boolean isVaultEnabled = false;

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            playerPointsAPI = PlayerPoints.getInstance().getAPI();
            isPlayerPointsEnabled = true;
            getLogger().info("Плагин PlayerPoints найден! Экономика с ним работать будет!");
        } else {
            getLogger().warning("Плагин PlayerPoints не найден! Экономика с ним работать не будет!");
        }

        if (setupVault()) {
            isVaultEnabled = true;
            getLogger().info("Плагин Vault найден! Экономика с ним работать будет!");
        } else {
            getLogger().warning("Плагин Vault не найден! Экономика с ним работать не будет!");
        }

        return isPlayerPointsEnabled || isVaultEnabled;
    }
}
