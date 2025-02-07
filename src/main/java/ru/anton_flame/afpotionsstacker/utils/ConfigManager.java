package ru.anton_flame.afpotionsstacker.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigManager {

    public static int defaultPotionLimit, splashPotionLimit, lingeringPotionLimit;
    public static String noPermission, reloaded;
    public static List<String> help;
    public static ConfigurationSection groupsLimitsSection;
    public static boolean luckPermsEnabled;

    public static void setupConfigValues(Plugin plugin) {
        defaultPotionLimit = plugin.getConfig().getInt("settings.limits.default_potion");
        splashPotionLimit = plugin.getConfig().getInt("settings.limits.splash_potion");
        lingeringPotionLimit = plugin.getConfig().getInt("settings.limits.lingering_potion");
        noPermission = plugin.getConfig().getString("messages.no-permission");
        reloaded = plugin.getConfig().getString("messages.reloaded");
        help = plugin.getConfig().getStringList("messages.help");
        groupsLimitsSection = plugin.getConfig().getConfigurationSection("settings.limits.luckperms");
        luckPermsEnabled = groupsLimitsSection.getBoolean("enabled");
    }
}
