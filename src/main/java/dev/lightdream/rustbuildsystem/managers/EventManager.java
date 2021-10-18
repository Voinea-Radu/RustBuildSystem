package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import dev.lightdream.rustbuildsystem.helpers.ItemBuilder;
import dev.lightdream.rustbuildsystem.inventory.BuildInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventManager implements Listener {

    private final Main plugin;
    public HashMap<User, BuildSession> buildMode = new HashMap<>();
    public List<User> upgradeMode = new ArrayList<>();
    public HashMap<User, Integer> playerMap = new HashMap<>();

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        User user = plugin.databaseManager.getUser(event.getPlayer());
        if (user.getPlayer() == null) {
            return;
        }
        if (!buildMode.containsKey(user)) {
            return;
        }

        if (Main.instance.config.stepsForUpdate != 0) {
            if (playerMap.containsKey(user)) {
                Integer i = playerMap.get(user);
                if (i < Main.instance.config.stepsForUpdate) {
                    i++;
                    playerMap.put(user, i);
                    return;
                }
                i = 0;
                playerMap.put(user, i);
            } else {
                playerMap.put(user, 0);
            }
        }

        //Preview the build session
        BuildSession buildSession = buildMode.get(user);
        buildSession.preview();
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        User user = plugin.databaseManager.getUser(event.getPlayer());

        if (user.getPlayer() == null) {
            return;
        }

        if (!buildMode.containsKey(user)) {
            return;
        }
        BuildSession buildSession = buildMode.get(user);
        buildSession.build(event.getPlayer());
        buildMode.remove(user);
        playerMap.remove(user);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        User user = plugin.databaseManager.getUser(event.getPlayer());
        Build build = plugin.databaseManager.getBuild(new PluginLocation(event.getBlock().getLocation()), true);

        if (build == null) {
            Build build1 = plugin.databaseManager.getBuild(new PluginLocation(event.getBlock().getLocation()));
            if (build1 == null) {
                return;
            }
            event.setCancelled(true);
            return;
        }

        if (upgradeMode.contains(user)) {
            if (build.ownerId == user.id) {
                build.upgrade();
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ANVIL_USE, 1, 1000);
                event.setCancelled(true);
                return;
            }
        }

        if (build.ownerId == user.id) {
            build.destroy();
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ANVIL_BREAK, 1, 1000);
            event.setCancelled(true);
            return;
        }

        Bukkit.getOnlinePlayers().forEach(players -> {
            if (players.getLocation().distance(event.getBlock().getLocation()) < 10){
                event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ZOMBIE_WOOD, 1, 500);
            }
        });
        build.damage();
        int percent = build.health * build.getMaxHealth() / 100;
        double b = Math.round(percent * 10.0) / 10.0 ;
        String color = "&a&l";
        if (b>=51.0) color = "&a&l";
        else if (b>=31.0)color = "&e&l";
        else if (b<=30.0) color = "&c&l";
        Utils.sendBar(event.getPlayer(), "&dIlosc HP: &7&l["+color+b+"%&7&l] &4\u2764");
        event.setCancelled(true);
    }

    @EventHandler
    public void interactBuilding(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        Player player = event.getPlayer();
        ItemStack is = player.getItemInHand();
        ItemBuilder plan = new ItemBuilder(Material.BOOK_AND_QUILL).setName("&5&lPLAN BUDOWY").setLore("&7xyz", "", "", "&7Kliknij PPM, aby otworzyc menu &dBUDOWANIA");
        if (is.isSimilar(plan.toItemStack())) {
            if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
            event.setCancelled(true);
            BuildInventory.openMain().openInventory(player);
        }
    }
}
