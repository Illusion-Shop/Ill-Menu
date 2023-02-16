package net.illusion.menu.event;

import net.illusion.core.data.Config;
import net.illusion.menu.data.MenuData;
import net.illusion.menu.data.MenuMapData;
import net.illusion.menu.util.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Inventory inv = event.getClickedInventory();
        if (event.getWhoClicked() instanceof Player player) {

            if (inv == null) return;

            if (inv.getHolder() instanceof MenuData) {
                MenuData menuData = MenuMapData.menuDataHashMap.get(player.getUniqueId());
                Config config = menuData.getConfig();

                for (String result : config.getConfig().getConfigurationSection("items").getKeys(false)) {
                    List<Integer> slots = config.config.getIntegerList("items." + result + ".slots");

                    if (slots.isEmpty()) {
                        int slot = config.getInt("items." + result + ".slot");

                        if (slot == event.getSlot()) {
                            if (event.isRightClick()) {
                                MenuUtils.runCommand(player, config, "items." + result + ".right_click_commands");
                            } else {
                                MenuUtils.runCommand(player, config, "items." + result + ".left_click_commands");
                            }
                            event.setCancelled(true);
                            break;
                        }
                    }

                    if (slots.contains(event.getSlot())) {
                        if (event.isRightClick()) {
                            MenuUtils.runCommand(player, config, "items." + result + ".right_click_commands");
                        } else {
                            MenuUtils.runCommand(player, config, "items." + result + ".left_click_commands");
                        }
                        event.setCancelled(true);

                        break;
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}
