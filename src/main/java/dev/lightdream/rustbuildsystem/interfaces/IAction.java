package dev.lightdream.rustbuildsystem.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @Author: Matthew 'delix'
 * @Created 26.09.2021
 * @Class: IAction
 **/

public interface IAction {
    void execute(Player p0, Inventory p1, int p2, ItemStack p3);
}

