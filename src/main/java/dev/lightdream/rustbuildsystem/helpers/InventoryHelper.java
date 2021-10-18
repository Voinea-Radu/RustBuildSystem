package dev.lightdream.rustbuildsystem.helpers;

import dev.lightdream.rustbuildsystem.interfaces.IAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Matthew 'delix'
 * @Created 26.09.2021
 * @Class: InventoryHelper
 **/

public class InventoryHelper implements Listener {

    private final Inventory inventory;
    private final Map<Integer, IAction> actions;

    public InventoryHelper(Plugin plugin, String title, int rows) {
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
        this.actions = new HashMap<Integer, IAction>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void setItem(int slot, ItemStack itemStack, IAction clickAction) {
        slot = ((slot > this.inventory.getSize()) ? (slot % this.inventory.getSize()) : slot);
        this.inventory.setItem(slot, itemStack);
        this.actions.put(slot, clickAction);
    }

    public InventoryHelper setAllItems(ItemStack[] items) {
        for (int i = 0; i < items.length; ++i) {
            this.inventory.setItem(i, items[i]);
        }
        return this;
    }

    public InventoryHelper setAction(int slot, IAction action) {
        if (action != null) {
            this.actions.put(slot, action);
        } else {
            this.actions.remove(slot);
        }
        return this;
    }

    public InventoryHelper setOpenAction(IAction action) {
        if (action != null) {
            this.actions.put(-1, action);
        } else {
            this.actions.remove(-1);
        }
        return this;
    }

    public ItemStack getItem(int i) {
        return this.inventory.getItem(i);
    }

    public InventoryHelper setCloseAction(IAction action) {
        if (action != null) {
            this.actions.put(-2, action);
        } else {
            this.actions.remove(-2);
        }
        return this;
    }

    public void openInventory(Player player) {
        player.openInventory(this.inventory);
    }

    public InventoryHelper openInventory(Player[] players) {
        for (Player player : players) {
            this.openInventory(player);
        }
        return this;
    }

    public InventoryHelper openInventory(Collection<? extends Player> players) {
        for (Player player : players) {
            this.openInventory(player);
        }
        return this;
    }

    public Inventory get() {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(this.inventory)) {
            e.setCancelled(true);
            IAction action = this.actions.get(e.getRawSlot());
            if (action != null) {
                action.execute((Player) e.getWhoClicked(), e.getInventory(), e.getRawSlot(), e.getInventory().getItem(e.getRawSlot()));
            }
            e.getCursor().setType(Material.AIR);
        }
    }

    @EventHandler
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        if (e.getDestination().equals(this.inventory)) {
            e.setCancelled(true);
            return;
        }
        if (e.getInitiator().equals(this.inventory)) {
            e.setCancelled(true);
            return;
        }
        if (this.inventory.equals(e.getSource())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().equals(this.inventory)) {
            IAction action = this.actions.get(-1);
            if (action != null) {
                action.execute((Player) e.getPlayer(), e.getInventory(), -1, null);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(this.inventory)) {
            IAction action = this.actions.get(-2);
            if (action != null) {
                action.execute((Player) e.getPlayer(), e.getInventory(), -1, null);
            }
        }
    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent e) {
        if (this.inventory.equals(e.getInventory())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (e.getInventory().equals(this.inventory)) {
            e.setCancelled(true);
        }
    }
}

