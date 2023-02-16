package net.illusion.menu.cmd;

import net.illusion.core.data.Config;
import net.illusion.core.util.text.StringUtil;
import net.illusion.menu.MenuPlugin;
import net.illusion.menu.data.MenuData;
import net.illusion.menu.data.MenuMapData;
import net.illusion.menu.util.MenuUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) {
            return true;
        }
        Player player = (Player) sender;

        String name;

        MenuData menuData;

        switch (args[0]) {
            case "열기":
                name = StringUtil.join(" ", args, 1);

                if (!isExist(player, name)) return false;

                menuData = new MenuData(name);
                menuData.open(player);
                MenuMapData.menuDataHashMap.put(player.getUniqueId(), menuData);

                break;

            case "생성":
                if (player.isOp()) {
                    name = StringUtil.join(" ", args, 1);

                    if (!compare(name, player)) return false;

                    if (!compareLength(name, player)) return false;

                    menuData = new MenuData(name);
                    menuData.create();

                    player.sendMessage(MenuPlugin.prefix + " §a성공적으로 메뉴를 만들었습니다.");
                }
                break;
            case "제거":
                if (player.isOp()) {
                    name = StringUtil.join(" ", args, 1);

                    if (!isExist(player, name)) return false;

                    menuData = new MenuData(name);
                    menuData.delete();

                    player.sendMessage(MenuPlugin.prefix + " §a성공적으로 메뉴를 제거하였습니다.");
                }
                break;
            case "리로드":
                if (player.isOp()) {
                    name = StringUtil.join(" ", args, 1);

                    if (!isExist(player, name)) return false;

                    menuData = new MenuData(name);
                    menuData.reloadConfig();

                    player.sendMessage(MenuPlugin.prefix + " §a메뉴 콘피그 리로드를 했습니다.");
                }
                break;
        }
        return false;
    }

    private boolean isExist(Player player, String name) {
        Config folder = new Config("menu/");
        folder.setPlugin(MenuPlugin.getPlugin());

        if (!folder.fileListName().contains(name)) {
            player.sendMessage(MenuPlugin.prefix + " " + name + "§c메뉴를 찾을 수 없습니다.");
            return false;
        }
        return true;
    }

    /**
     * 이름을 특정 형식에 맞춰 입력했는지 비교하는 메소드 입니다.
     *
     * @param name   비교할 이름
     * @param player 플레이어
     * @return 만약 입력한 이름이 잘못된 형식이면, false를, 반대 경우엔 true를 반환합니다. 따라서
     * <p>if (!compare(name, player)) return false; 한줄로 비교가 가능해집니다.</p>
     */
    private boolean compare(String name, Player player) {
        if (name.length() == 0) {
            player.sendMessage(MenuPlugin.prefix + "§c상점 이름을 입력해 주세요!");
            return false;
        }

        if (StringUtil.containsSpecialChar(name)) {
            player.sendMessage(MenuPlugin.prefix + "§c상점 이름에 특수문자는 들어갈 수 없습니다!");
            return false;
        }

        if (MenuUtils.getFolderFiles().contains(name)) {
            player.sendMessage(MenuPlugin.prefix + " §c같은 이름의 상점은 만들지 못합니다.");
            return false;
        }

        return true;
    }


    /**
     * 윈도우10 기준, 파일 이름의 최대 길이는 256자 입니다.
     * 즉, 이름이 256자를 넘었는지 확인하는 메소드 입니다.
     *
     * @param name   비교할 이름
     * @param player 커맨드를 입력한 플레이어
     * @return 256자가 넘으면 false를, 넘지 않으면 true를 반환합니다.
     */
    private boolean compareLength(String name, Player player) {
        if (name.length() > 256) {
            player.sendMessage(MenuPlugin.prefix + "§c이름의 최대 길이는 256자 까지 가능합니다!");
            return false;
        }
        return true;
    }
}
