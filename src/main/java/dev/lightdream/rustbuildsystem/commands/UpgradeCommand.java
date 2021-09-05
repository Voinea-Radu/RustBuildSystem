package dev.lightdream.rustbuildsystem.commands;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.commands.Command;
import dev.lightdream.api.databases.User;
import dev.lightdream.rustbuildsystem.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class UpgradeCommand extends Command {
    public UpgradeCommand(@NotNull LightDreamPlugin plugin) {
        super(plugin, Collections.singletonList("upgrade"), "", "", true, false, "[foundation]");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        User user = Main.instance.databaseManager.getUser((Player) commandSender);

        if (Main.instance.eventManager.upgradeMode.contains(user)) {
            Main.instance.eventManager.upgradeMode.remove(user);
        } else {
            Main.instance.eventManager.upgradeMode.add(user);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
