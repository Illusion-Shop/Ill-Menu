package net.illusion.menu.data;

import lombok.Getter;
import net.illusion.core.data.Config;
import net.illusion.core.util.text.StringUtil;
import net.illusion.menu.MenuPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuData implements InventoryHolder {

    private Config defaultConfig;

    @Getter
    private Config config;

    private String name;

    public MenuData(String name) {
        defaultConfig = new Config("default");
        defaultConfig.setPlugin(MenuPlugin.getPlugin());
        config = new Config("menu/" + name);
        config.setPlugin(MenuPlugin.getPlugin());
        this.name = name;
    }


    public void create() {
        defaultConfig.loadDefualtConfig();
        defaultConfig.renameFile("menu/" + name);
    }

    public void delete() {
        config.delete();
    }

    public void reloadConfig() {
        config.reloadConfig();
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    @NotNull
    @Override
    public Inventory getInventory() {

        Inventory inv = Bukkit.createInventory(this, this.config.getInt("size") * 9, this.config.getString("menu_title"));

        for (String result : config.getConfig().getConfigurationSection("items").getKeys(false)) {

            Material material = Material.valueOf(config.getConfig().getString("items." + result + ".material"));

            List<Integer> slots = config.config.getIntegerList("items." + result + ".slots");

            int customModelData = config.getInt("items." + result + ".custommodeldata");
            String display = config.getString("items." + result + ".display_name");
            List<String> lore = StringUtil.translateColors(config.getStringList("items." + result + ".lore"));

            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(display);
            itemMeta.setLore(lore);
            itemMeta.setCustomModelData(customModelData);

            itemStack.setItemMeta(itemMeta);

            if (slots.isEmpty()) {
                int slot = config.getInt("items." + result + ".slot");
                System.out.println(slot);
                inv.setItem(slot, itemStack);
            }

            slots.forEach(slot -> {
                inv.setItem(slot, itemStack);
            });
        }
        return inv;
    }
}
