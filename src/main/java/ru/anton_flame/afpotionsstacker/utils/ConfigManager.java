package ru.anton_flame.afpotionsstacker.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ConfigManager {

    public static int defaultPotionLimit, splashPotionLimit, lingeringPotionLimit;
    public static String noPermission, reloaded, potionsStacking, notEnoughCurrency, economyNotFound;
    public static List<String> help;
    public static ConfigurationSection groupsLimitsSection, defaultPotionPayment, splashPotionPayment, lingeringPotionPayment;
    public static boolean luckPermsEnabled;

    public static void setupConfigValues(Plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection settings = config.getConfigurationSection("settings");
        ConfigurationSection limits = settings.getConfigurationSection("limits");
        ConfigurationSection payment = settings.getConfigurationSection("payment");
        ConfigurationSection messages = config.getConfigurationSection("messages");

        defaultPotionLimit = limits.getInt("default_potion");
        splashPotionLimit = limits.getInt("splash_potion");
        lingeringPotionLimit = limits.getInt("lingering_potion");
        defaultPotionPayment = payment.getConfigurationSection("default_potion");
        splashPotionPayment = payment.getConfigurationSection("splash_potion");
        lingeringPotionPayment = payment.getConfigurationSection("lingering_potion");
        noPermission = Hex.color(messages.getString("no-permission"));
        reloaded = Hex.color(messages.getString("reloaded"));
        help = Hex.color(messages.getStringList("help"));
        potionsStacking = Hex.color(messages.getString("potions-stacking"));
        notEnoughCurrency = Hex.color(messages.getString("not-enough-currency"));
        economyNotFound = Hex.color(messages.getString("economy-not-found"));
        groupsLimitsSection = limits.getConfigurationSection("luckperms");
        luckPermsEnabled = groupsLimitsSection.getBoolean("enabled");
    }
}
