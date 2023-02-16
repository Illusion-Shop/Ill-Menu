package net.illusion.menu;

import net.illusion.core.data.Config;
import net.illusion.menu.cmd.MenuCmd;
import net.illusion.menu.cmd.MenuCmdTab;
import net.illusion.menu.event.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MenuPlugin extends JavaPlugin {

    private final Logger log = Bukkit.getLogger();
    private static MenuPlugin plugin;
    public static Config config;

    public static String prefix;

    @Override
    public void onEnable() {
        // DEPENDENCY
        if (getServer().getPluginManager().getPlugin("Ill-Core") != null) {
            log.warning("[ " + Bukkit.getName() + "] Ill-Core 플러그인이 적용되지 않아 비활성화 됩니다.");
            log.warning("[ " + Bukkit.getName() + "] 다운로드 링크: https://discord.gg/illusion-shop");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // CONFIG
        plugin = this;
        config = new Config("config");
        config.setPlugin(this);
        config.loadDefualtConfig();

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix"));

        // EVENT
        // TODO
        Bukkit.getPluginManager().registerEvents(new MenuListener(),this);

        // COMMAND
        getCommand("메뉴").setExecutor(new MenuCmd());
        getCommand("메뉴").setTabCompleter(new MenuCmdTab());
    }

    @Override
    public void onDisable() {
        // TODO
    }

    public static MenuPlugin getPlugin() {
        return plugin;
    }
}
