package dev.lightdream.rustbuildsystem.commands;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.commands.Command;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.utils.MessageUtils;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class BuildCommand extends Command {
    public BuildCommand(@NotNull LightDreamPlugin plugin) {
        super(plugin, Collections.singletonList("build"), "", "", true, false, "[foundation]");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        if (args.size() != 1) {
            sendUsage(commandSender);
            return;
        }

        String type = args.get(0);

        BuildSchematic schematic = Main.instance.config.getBuild(type);

        if (schematic == null) {
            MessageUtils.sendMessage(commandSender, Main.instance.lang.invalidBuild);
            return;
        }
        User user = Main.instance.databaseManager.getUser((Player) commandSender);

        if (Main.instance.eventManager.buildMode.containsKey(user)) {
            Main.instance.eventManager.buildMode.get(user).clearPlaceholders();
        }

        Main.instance.eventManager.buildMode.put(user, new BuildSession(user, schematic));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
