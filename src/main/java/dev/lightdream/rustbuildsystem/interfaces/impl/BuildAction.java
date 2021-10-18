package dev.lightdream.rustbuildsystem.interfaces.impl;

import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.interfaces.IAction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @Author: Matthew 'delix'
 * @Created 27.09.2021
 * @Class: BuildAction
 **/

public class BuildAction implements IAction {

    private final String actionType;

    public BuildAction(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public void execute(Player p, Inventory inventory, int slot, ItemStack itemStack) {
        p.closeInventory();
        if (actionType.equalsIgnoreCase("spiral")) p.performCommand("rbs build spiral_stairs1");
        if (actionType.equalsIgnoreCase("error")) p.sendMessage(Utils.colored("&dBlad: &7Wybierz inny rodzaj budowli!"));
        if (actionType.equalsIgnoreCase("foundation")) p.performCommand("rbs build foundation");
        if (actionType.equalsIgnoreCase("oblique_foundation")) p.performCommand("rbs build foundation_oblique1");
        if (actionType.equalsIgnoreCase("wall")) p.performCommand("rbs build wall");
        if (actionType.equalsIgnoreCase("wall-onewindow")) p.performCommand("rbs build one_window");
        if (actionType.equalsIgnoreCase("wall-triplewindow")) p.performCommand("rbs build triple_window");
        if (actionType.equalsIgnoreCase("wall-triplewindow")) p.performCommand("rbs build triple_window");
        if (actionType.equalsIgnoreCase("roof-four")) p.performCommand("rbs build roof_four");
        if (actionType.equalsIgnoreCase("oblique-wall")) p.performCommand("rbs build wall_oblique1");
        if (actionType.equalsIgnoreCase("roof-one")) p.performCommand("rbs build roof_one");
        if (actionType.equalsIgnoreCase("oblique-wall2")) p.performCommand("rbs build window_oblique1");
        if (actionType.equalsIgnoreCase("roof")) p.performCommand("rbs build roof");
        if (actionType.equalsIgnoreCase("door")) p.performCommand("rbs build door");
        if (actionType.equalsIgnoreCase("wall-one")) p.performCommand("rbs build entrance");
        if (actionType.equalsIgnoreCase("wall-duo")) p.performCommand("rbs build entrance");
    }
}