package dev.lightdream.rustbuildsystem.commands;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.commands.Command;
import dev.lightdream.rustbuildsystem.helpers.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @Author: Matthew 'delix'
 * @Created 26.09.2021
 * @Class: GiveCommand
 **/

public class GiveCommand extends Command {
    public GiveCommand(@NotNull LightDreamPlugin plugin) {
        super(plugin, Collections.singletonList("give"), "", "", true, false, "");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        ItemBuilder plan = new ItemBuilder(Material.BOOK_AND_QUILL).setName("&5&lPLAN BUDOWY").setLore("&7xyz", "", "", "&7Kliknij PPM, aby otworzyc menu &dBUDOWANIA");
        Player player = (Player)commandSender;
        player.getInventory().addItem(plan.toItemStack());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
