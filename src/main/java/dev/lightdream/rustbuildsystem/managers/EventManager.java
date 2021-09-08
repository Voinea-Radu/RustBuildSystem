package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.utils.MessageUtils;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventManager implements Listener {

    private final Main plugin;
    public HashMap<User, BuildSession> buildMode = new HashMap<>();
    public List<User> upgradeMode = new ArrayList<>();

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

        //Preview the build session
        BuildSession buildSession = buildMode.get(user);
        buildSession.preview();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        User user = plugin.databaseManager.getUser(event.getPlayer());

        if (user.getPlayer() == null) {
            return;
        }

        if (!buildMode.containsKey(user)) {
            return;
        }
        BuildSession buildSession = buildMode.get(user);
        buildSession.build();
        buildMode.remove(user);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        User user = plugin.databaseManager.getUser(event.getPlayer());
        Build build = plugin.databaseManager.getBuild(new PluginLocation(event.getBlock().getLocation()));


        if (build == null) {
            return;
        }

        if (upgradeMode.contains(user)) {
            if (build.ownerId == user.id){
                build.upgrade();
                event.setCancelled(true);
                return;
            }
        }

        if (build.ownerId == user.id) {
            build.destroy();
            event.setCancelled(true);
            return;
        }

        build.damage();
        MessageUtils.sendMessage(user, Main.instance.lang.healthMessage.replace("%health%", String.valueOf(build.health)));
        event.setCancelled(true);
    }

}