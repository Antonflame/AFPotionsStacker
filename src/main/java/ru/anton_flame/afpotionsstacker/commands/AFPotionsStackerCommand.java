package ru.anton_flame.afpotionsstacker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.anton_flame.afpotionsstacker.AFPotionsStacker;
import ru.anton_flame.afpotionsstacker.utils.Hex;

public class AFPotionsStackerCommand implements CommandExecutor {

    private final AFPotionsStacker plugin;
    public AFPotionsStackerCommand(AFPotionsStacker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 1 || !strings[0].equalsIgnoreCase("reload")) {
            for (String message : plugin.getConfig().getStringList("messages.help")) {
                commandSender.sendMessage(Hex.color(message));
            }
            return false;
        }

        if (strings.length == 1 && strings[0].equalsIgnoreCase("reload")) {
            if (!commandSender.hasPermission("afpotionsstacker.reload")) {
                commandSender.sendMessage(Hex.color(plugin.getConfig().getString("messages.no-permission")));
                return false;
            }

            plugin.reloadConfig();
            commandSender.sendMessage(Hex.color(plugin.getConfig().getString("messages.reloaded")));
        }
        return true;
    }
}
