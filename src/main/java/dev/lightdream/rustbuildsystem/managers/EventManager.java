package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        User user = plugin.databaseManager.getUser(event.getPlayer());
        if (!buildMode.containsKey(user)) {
            return;
        }

        //Getting the placeholders
        BuildSession buildSession = buildMode.get(user);

        //Clear the placeholders
        buildSession.clearPlaceholders();

        //Prepare the placeholders
        List<PluginLocation> placeholders = new ArrayList<>();

        //Get the target block
        Block target = Utils.getTargetBlock(user.getPlayer(), 8, false);
        if (target == null) {
            return;
        }

        //Set the root to the target block
        PluginLocation root = new PluginLocation(target.getLocation());

        //Get if there are any builds on the target block
        Build build = plugin.databaseManager.getBuild(root);
        if (build == null && !buildSession.schematic.placedOnGround) {
            return;
        }
        if (build != null && buildSession.schematic.placedOnGround) {
            return;
        }

        if (!buildSession.schematic.placedOnGround) {
            //Get the foundation of the build
            Build foundation = build.getFoundation();

            if (buildSession.schematic.placeOnMargin) {
                PluginLocation location = null;
                double minDistance = 1000000000;
                for (PluginLocation wallRoot : foundation.getWallRoots()) {
                    double distance = root.toLocation().distance(wallRoot.toLocation());
                    if (minDistance > distance) {
                        minDistance = distance;
                        location = wallRoot;
                    }
                }
                root = location;
            } else {
                root = foundation.getRootLocation();
            }
        }
        if (root == null) {
            return;
        }

        boolean canBuild = true;
        buildSession.rotate = root.rotationX == 90;
        root = root.newOffset(buildSession.schematic.rootOffset);
        buildSession.root = root;

        for (Position position : buildSession.schematic.offsets.keySet()) {
            Position offset = position.clone();
            if (buildSession.rotate) {
                offset.flip();
            }
            PluginLocation location = root.newOffset(offset);

            if (location.getBlock().getType().equals(Material.AIR)) {
                if (canBuild) {
                    location.setBlock(XMaterial.LIME_STAINED_GLASS.parseMaterial());
                    location.getBlock().setData(XMaterial.LIME_STAINED_GLASS.getData());
                } else {
                    location.setBlock(XMaterial.RED_STAINED_GLASS.parseMaterial());
                    location.getBlock().setData(XMaterial.RED_STAINED_GLASS.getData());
                }
                location.getBlock().getState().update();
                placeholders.add(location);
            } else {
                if (buildSession.schematic.placeOnMargin) {
                    if (plugin.databaseManager.getBuild(location) != null) {
                        if (!root.equals(plugin.databaseManager.getBuild(location).getRootLocation())) {
                            continue;
                        }
                    }
                }

                canBuild = false;
                placeholders.forEach(l -> {
                    l.setBlock(XMaterial.RED_STAINED_GLASS.parseMaterial());
                    l.getBlock().setData(XMaterial.RED_STAINED_GLASS.getData());
                });
            }
        }

        buildSession.placeholderBlocks = placeholders;
        buildSession.canBuild = canBuild;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        User user = plugin.databaseManager.getUser(event.getPlayer());

        if (!buildMode.containsKey(user)) {
            return;
        }
        BuildSession buildSession = buildMode.get(user);
        buildMode.remove(user);
        buildSession.placeholderBlocks.forEach(location -> location.setBlock(Material.AIR));

        if (buildSession.root == null) {
            return;
        }
        if (!buildSession.canBuild) {
            return;
        }
        if(!buildSession.schematic.cost.has(user.getPlayer())){
            return;
        }
        buildSession.schematic.cost.take(user.getPlayer());

        for (PluginLocation location : buildSession.placeholderBlocks) {
            PluginLocation offset = location.newUnOffset(buildSession.root);
            if (buildSession.rotate) {
                double tmp = offset.x;
                offset.x = offset.z;
                offset.z = tmp;
            }
            location.setBlock(buildSession.schematic.offsets.get(offset.toPosition()).parseMaterial());
        }

        plugin.databaseManager.save(
                new Build(user.id, buildSession.schematic.type,
                        buildSession.schematic.type.equals("foundation") ? -1 : plugin.databaseManager.getBuild(buildSession.root.newUnOffset(buildSession.schematic.rootOffset)).id,
                        buildSession.root,
                        buildSession.placeholderBlocks)
        );
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Build build = plugin.databaseManager.getBuild(new PluginLocation(event.getBlock().getLocation()));
        if (build == null) {
            return;
        }
        build.destroy();

    }
}
