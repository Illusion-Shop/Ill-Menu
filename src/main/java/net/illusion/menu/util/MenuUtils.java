package net.illusion.menu.util;

import net.illusion.core.data.Config;
import net.illusion.menu.MenuPlugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class MenuUtils {
    public static void runCommand(Player player, Config config, String path) {
        List<String> commands = config.getStringList(path);

        for (String command : commands) {
            if (command.contains("[player]")) {
                player.performCommand(command.replace("[player] ", ""));
            } else if (command.contains("[sound]")) {
                Sound sound = Sound.valueOf(command.replace("[sound] ", ""));
                player.playSound(player.getLocation(), sound, 1, 1);
            }
        }
    }

    public static List<String> getFolderFiles() {
        Config folder = new Config("menu/");
        folder.setPlugin(MenuPlugin.getPlugin());
        return folder.fileListName();
    }
}
