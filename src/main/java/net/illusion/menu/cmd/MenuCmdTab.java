package net.illusion.menu.cmd;

import net.illusion.core.data.Config;
import net.illusion.menu.MenuPlugin;
import net.illusion.menu.util.MenuUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuCmdTab implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            List<String> result = new ArrayList<>();
            if (args.length == 1) {
                result.add("열기");
                if (player.isOp())
                    result = List.of("열기", "생성", "제거", "리로드");

                return result;
            } else if (args.length == 2) {
                if (List.of("열기", "제거").contains(args[0]) && player.isOp()) {
                    return MenuUtils.getFolderFiles();
                }
            }
        }

        return Collections.EMPTY_LIST;
    }
}
