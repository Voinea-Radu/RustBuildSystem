package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.api.utils.MessageUtils;
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

        //Getting the placeholders
        BuildSession buildSession = buildMode.get(user);

        //Get the target block
        Block target = Utils.getTargetBlock(user.getPlayer(), 8, false);
        if (target == null) {
            return;
        }

        //Set the root to the target block
        PluginLocation root = new PluginLocation(target.getLocation());

        //If the the player location is to close
        if (root.toLocation().distance(user.getPlayer().getLocation()) <= 2) {
            return;
        }

        if (buildSession.buildTarget != null) {
            if (buildSession.buildTarget.equals(root)) {
                return;
            }
        }
        buildSession.buildTarget = root.clone();

        //Clear the placeholders
        buildSession.clearPlaceholders(user.getPlayer());
        buildSession.canBuild = false;
        buildSession.canCollideWithFoundation = false;

        //Prepare the placeholders
        List<PluginLocation> placeholders = new ArrayList<>();

        //Get if there are any builds on the target block
        Build build = plugin.databaseManager.getBuild(root);

        if (build == null && !buildSession.schematic.placedOnGround) {
            return;
        }

        if (!buildSession.schematic.placedOnGround) {
            //Get the foundation of the build
            Build foundation = build.getFoundation();

            if (buildSession.isFoundation()) {
                List<Build> walls = plugin.databaseManager.getBuilds(foundation.id, "wall");
                if (walls.size() == 0) {
                    return;
                }
            }

            if (buildSession.schematic.placeOnMargin) {
                root = build.getClosestMarginRoot(root, false);
            } else {
                buildSession.canCollideWithFoundation = true;
                root = foundation.getRootLocation();
            }
        } else {
            if (build != null) {
                if (!build.isFoundation()) {
                    PluginLocation check = root.clone();
                    for (int i = 0; i <= 255; i++) {
                        check.y = i;
                        if (plugin.databaseManager.getBuild(check) != null) {
                            return;
                        }
                    }
                } else {
                    root = build.getClosestMarginRoot(root, true);
                    buildSession.canCollideWithFoundation = true;
                    if (root.rotationX == 90) {
                        root.offset(0, -2, -2);
                    } else if (root.rotationX == 0) {
                        root.offset(-2, -2, 0);
                    } else if (root.rotationX == 180) {
                        root.offset(2, -2, 0);
                    } else if (root.rotationX == 270) {
                        root.offset(0, -2, 2);
                    }
                }
            } else {
                PluginLocation check = root.clone();
                for (int i = 0; i <= 255; i++) {
                    check.y = i;
                    if (plugin.databaseManager.getBuild(check) != null) {
                        return;
                    }
                }
            }
        }
        if (root == null) {
            return;
        }

        boolean canBuild = true;
        buildSession.rotate = root.rotationX == 90;
        root = root.newOffset(buildSession.schematic.rootOffset);
        buildSession.root = root;

        build = plugin.databaseManager.getBuild(root);
        if (build != null) {
            return;
        }

        for (Position position : buildSession.schematic.offsets.keySet()) {
            Position offset = position.clone();
            if (buildSession.rotate) {
                offset.flip();
            }
            PluginLocation location = root.newOffset(offset);
            if (buildSession.canCollideWithFoundation) {
                Build b = plugin.databaseManager.getBuild(location);
                if (b != null && b.isFoundation()) {
                    if (canBuild) {
                        user.getPlayer().sendBlockChange(location.toLocation(), XMaterial.LIME_STAINED_GLASS.parseMaterial(), XMaterial.LIME_STAINED_GLASS.getData());
                    } else {
                        user.getPlayer().sendBlockChange(location.toLocation(), XMaterial.RED_STAINED_GLASS.parseMaterial(), XMaterial.RED_STAINED_GLASS.getData());
                    }
                    placeholders.add(location);
                    continue;
                }
            }
            if (location.getBlock().getType().equals(Material.AIR)) {
                if (canBuild) {
                    user.getPlayer().sendBlockChange(location.toLocation(), XMaterial.LIME_STAINED_GLASS.parseMaterial(), XMaterial.LIME_STAINED_GLASS.getData());
                } else {
                    user.getPlayer().sendBlockChange(location.toLocation(), XMaterial.RED_STAINED_GLASS.parseMaterial(), XMaterial.RED_STAINED_GLASS.getData());
                }
                placeholders.add(location);
                continue;
            }
            if (buildSession.schematic.placeOnMargin) {
                if (plugin.databaseManager.getBuild(location) != null) {
                    if (!root.equals(plugin.databaseManager.getBuild(location).getRootLocation())) {
                        continue;
                    }
                }
            }

            canBuild = false;
            placeholders.forEach(l -> {
                user.getPlayer().sendBlockChange(l.toLocation(), XMaterial.RED_STAINED_GLASS.parseMaterial(), XMaterial.RED_STAINED_GLASS.getData());
            });

        }

        buildSession.placeholderBlocks = placeholders;
        buildSession.canBuild = canBuild;
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
        buildMode.remove(user);
        buildSession.clearPlaceholders(user.getPlayer());

        if (buildSession.root == null) {
            return;
        }
        if (!buildSession.canBuild) {
            return;
        }
        if (!buildSession.schematic.cost.get(0).has(user.getPlayer())) {
            return;
        }
        buildSession.schematic.cost.get(0).take(user.getPlayer());

        for (PluginLocation location : buildSession.placeholderBlocks) {
            PluginLocation offset = location.newUnOffset(buildSession.root);
            if (buildSession.rotate) {
                double tmp = offset.x;
                offset.x = offset.z;
                offset.z = tmp;
            }
            location.setBlock(buildSession.schematic.offsets.get(offset.toPosition()).get(0).parseMaterial());
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
            return;
        }

        build.damage();
        MessageUtils.sendMessage(user, Main.instance.lang.healthMessage.replace("%health%", String.valueOf(build.health)));
    }

}
