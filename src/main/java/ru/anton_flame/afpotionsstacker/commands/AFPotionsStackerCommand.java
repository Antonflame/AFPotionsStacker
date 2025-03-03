package ru.anton_flame.afpotionsstacker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.anton_flame.afpotionsstacker.AFPotionsStacker;
import ru.anton_flame.afpotionsstacker.utils.ConfigManager;

public class AFPotionsStackerCommand implements CommandExecutor {

    private final AFPotionsStacker plugin;
    public AFPotionsStackerCommand(AFPotionsStacker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 1 || !strings[0].equalsIgnoreCase("reload")) {
            for (String message : ConfigManager.help) {
                commandSender.sendMessage(message);
            }
            return false;
        }

        if (strings.length == 1 && strings[0].equalsIgnoreCase("reload")) {
            if (!commandSender.hasPermission("afpotionsstacker.reload")) {
                commandSender.sendMessage(ConfigManager.noPermission);
                return false;
            }

            plugin.reloadConfig();
            ConfigManager.setupConfigValues(plugin);
            commandSender.sendMessage(ConfigManager.reloaded);
        }
        return true;
    }
}
