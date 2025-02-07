package ru.anton_flame.afpotionsstacker.listeners;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.anton_flame.afpotionsstacker.AFPotionsStacker;
import ru.anton_flame.afpotionsstacker.utils.ConfigManager;

public class EventListener implements Listener {

    private final AFPotionsStacker plugin;
    public EventListener(AFPotionsStacker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack itemStack = event.getItem().getItemStack();
            if (itemStack.getType().toString().contains("POTION")) {
                String primaryGroup = plugin.luckPerms.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
                int maxAmount = getMaxAmount(primaryGroup, itemStack.getType());
                potionsStacking(event, player.getInventory(), itemStack, maxAmount);
            }
        }
    }

    private int getMaxAmount(String primaryGroup, Material type) {
        boolean isLuckPermsEnabled = ConfigManager.luckPermsEnabled;
        ConfigurationSection groupsLimitsSection = ConfigManager.groupsLimitsSection;

        String configKey;
        int defaultValue;

        if (type == Material.POTION) {
            configKey = "default_potion";
            defaultValue = ConfigManager.defaultPotionLimit;
        } else if (type == Material.SPLASH_POTION) {
            configKey = "splash_potion";
            defaultValue = ConfigManager.splashPotionLimit;
        } else if (type == Material.LINGERING_POTION) {
            configKey = "lingering_potion";
            defaultValue = ConfigManager.lingeringPotionLimit;
        } else {
            return 0;
        }

        if (isLuckPermsEnabled && groupsLimitsSection.contains(primaryGroup + "." + configKey)) {
            return groupsLimitsSection.getInt(primaryGroup + "." + configKey);
        }

        return defaultValue;
    }

    private void potionsStacking(EntityPickupItemEvent event, Inventory inventory, ItemStack itemStack, int maxAmount) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (stack != null && stack.isSimilar(itemStack) && stack.getAmount() < maxAmount) {
                int freeSpace = maxAmount - stack.getAmount();
                int amount = itemStack.getAmount();

                if (amount <= freeSpace) {
                    event.setCancelled(true);
                    event.getItem().remove();
                    stack.setAmount(stack.getAmount() + amount);
                    break;
                } else {
                    stack.setAmount(maxAmount);
                    itemStack.setAmount(amount - freeSpace);
                }
            }
        }
    }
}
