package ru.anton_flame.afpotionsstacker;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.anton_flame.afpotionsstacker.commands.AFPotionsStackerCommand;
import ru.anton_flame.afpotionsstacker.listeners.EventListener;
import ru.anton_flame.afpotionsstacker.utils.ConfigManager;

public final class AFPotionsStacker extends JavaPlugin {

    public LuckPerms luckPerms;

    @Override
    public void onEnable() {
        getLogger().info("Плагин был включен!");
        saveDefaultConfig();
        ConfigManager.setupConfigValues(this);
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
}
