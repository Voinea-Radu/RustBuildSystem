package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.database.Build;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"deprecation"})
@AllArgsConstructor
@NoArgsConstructor
public class BuildSession {

    public User user;
    public BuildSchematic schematic;
    public List<PluginLocation> placeholders;
    public PluginLocation root;
    public boolean rotate;
    public boolean canCollideWithFoundation;
    public Build targetBuild;
    public List<Build> collidingFoundations;
    public boolean canCollide;
    public PluginLocation lastPreviewLocation;

    public BuildSession(User user, BuildSchematic schematic) {
        this.user = user;
        this.schematic = schematic;
        this.placeholders = new ArrayList<>();
        this.root = null;
        this.rotate = false;
        this.canCollideWithFoundation = false;
        this.targetBuild = null;
        this.collidingFoundations = new ArrayList<>();
        this.canCollide = false;
        this.lastPreviewLocation = null;
    }

    public void clearPlaceholders() {
        placeholders.forEach(location -> {
            user.getPlayer().sendBlockChange(location.toLocation(), location.getBlock().getType(), location.getBlock().getData());
        });
    }

    public boolean isFoundation() {
        return schematic.type.equals("foundation");
    }

    public boolean isRoof() {
        return schematic.type.equals("roof");
    }

    public void preview() {

        //Get the target block
        Block target = Utils.getTargetBlock(user.getPlayer(), 8, false, true);

        if (this.root != null) {
            if (this.root.equals(new PluginLocation(target.getLocation()))) {
                return;
            }
        }

        //Set the target root to the target block
        this.root = new PluginLocation(target.getLocation());

        if (this.root.equals(lastPreviewLocation)) {
            return;
        }else{
            this.lastPreviewLocation =this.root;
        }

        if (isFoundation()) {
            this.canCollide = true;
            if (target.getType().equals(Material.AIR)) {
                PluginLocation check = this.root.newOffset(new Position(0, -2, 0));
                if (check.getBlock().getType().equals(Material.AIR)) {
                    return;
                }
            }
        } else {
            if (target.getType().equals(Material.AIR)) {
                return;
            }
        }

        if (this.root.y >= Main.instance.config.maxY) {
            return;
        }


        //If the the player location is to close return
        if (this.root.toLocation().distance(user.getPlayer().getLocation()) <= 2) {
            return;
        }

        //Clear the old sub-session
        clearPlaceholders();
        this.placeholders = new ArrayList<>();
        this.canCollideWithFoundation = false;

        //Get if there are any builds on the root
        this.targetBuild = Main.instance.databaseManager.getBuild(this.root);

        //If the build is null the session must be able to be placed on ground
        if (this.targetBuild == null && !this.schematic.placedOnGround) {
            return;
        }

        if (!this.schematic.placedOnGround) {
            Build foundation = this.targetBuild.getFoundation();

            if (this.isRoof()) {
                if (foundation.getWalls().size() == 0) {
                    return;
                }
            }

            if (this.schematic.placeOnMargin) {
                this.root = this.targetBuild.getClosestMarginRoot(this.root, false, false);
            } else {
                this.canCollideWithFoundation = true;
                this.root = foundation.getRootLocation();
            }
        } else {
            if (this.targetBuild == null) {
                List<Position> positions = Arrays.asList(
                        new Position(-3, 0, -3),
                        new Position(-3, 0, 0),
                        new Position(-3, 0, 3),
                        new Position(0, 0, -3),
                        new Position(0, 0, 0),
                        new Position(0, 0, 3),
                        new Position(3, 0, -3),
                        new Position(3, 0, 0),
                        new Position(3, 0, 3)
                );

                for (Position offset : positions) {
                    PluginLocation check = this.root.newOffset(offset);
                    for (int i = (int) (this.root.y - 2); i <= this.root.y + 2; i++) {
                        check.y = i;
                        if (Main.instance.databaseManager.getBuild(check) != null) {
                            return;
                        }
                    }
                }
            } else {
                if (this.targetBuild.isFoundation()) {
                    this.root = this.targetBuild.getClosestMarginRoot(this.root, true, true);
                    this.canCollideWithFoundation = true;
                    //Apply the rotation
                    switch ((int) this.root.rotationX) {
                        case 0:
                            this.root.offset(2, -1, 0);
                            break;
                        case 45:
                            this.root.offset(2, -1, 2);
                            break;
                        case 90:
                            this.root.offset(0, -1, 2);
                            break;
                        case 135:
                            this.root.offset(-2, -1, 2);
                            break;
                        case 180:
                            this.root.offset(-2, -1, 0);
                            break;
                        case 225:
                            this.root.offset(-2, -1, -2);
                            break;
                        case 270:
                            this.root.offset(0, -1, -2);
                            break;
                        case 315:
                            this.root.offset(2, -1, -2);
                            break;
                    }
                } else {
                    List<Position> positions = Arrays.asList(
                            new Position(-2, 0, -2),
                            new Position(2, 0, 2),
                            new Position(2, 0, -2),
                            new Position(-2, 0, 2)
                    );

                    for (Position offset : positions) {
                        for (Position offset2 : this.schematic.offsets.keySet()) {
                            PluginLocation check = this.root.newOffset(offset);
                            check.offset(offset2);

                            for (int i = (int) (this.root.y - 10); i <= this.root.y; i++) {
                                check.y = i;
                                if (Main.instance.databaseManager.getBuild(check) != null) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (this.root == null) {
            return;
        }

        boolean canBuild = true;
        this.rotate = this.root.rotationX == 90;
        this.root = this.root.newOffset(this.schematic.rootOffset);

        if (Main.instance.databaseManager.getBuild(this.root) != null) {
            return;
        }

        for (Position position : this.schematic.offsets.keySet()) {
            Position offset = position.clone();
            if (this.rotate) {
                offset.flip();
            }
            PluginLocation location = this.root.newOffset(offset);

            if (this.canCollideWithFoundation) {
                Build b = Main.instance.databaseManager.getBuild(location);

                if (b != null && b.isFoundation()) {
                    user.getPlayer().sendBlockChange(location.toLocation(), canBuild ? XMaterial.LIME_STAINED_GLASS.parseMaterial() : XMaterial.RED_STAINED_GLASS.parseMaterial(),
                            canBuild ? XMaterial.LIME_STAINED_GLASS.getData() : XMaterial.LIME_STAINED_GLASS.getData());
                    placeholders.add(location);
                    continue;
                }
            }

            Build b = Main.instance.databaseManager.getBuild(location);
            if (b != null) {
                if (this.schematic.type.equals(b.type)) {
                    continue;
                }
            }

            if (location.getBlock().getType().equals(Material.AIR)) {
                user.getPlayer().sendBlockChange(location.toLocation(), canBuild ? XMaterial.LIME_STAINED_GLASS.parseMaterial() : XMaterial.RED_STAINED_GLASS.parseMaterial(),
                        canBuild ? XMaterial.LIME_STAINED_GLASS.getData() : XMaterial.LIME_STAINED_GLASS.getData());
                placeholders.add(location);
                continue;
            }

            if (this.canCollide) {
                continue;
            }

            canBuild = false;
            placeholders.forEach(l -> user.getPlayer().sendBlockChange(l.toLocation(), XMaterial.RED_STAINED_GLASS.parseMaterial(), XMaterial.RED_STAINED_GLASS.getData()));
        }

        if (!canBuild) {
            this.root = null;
        }
    }

    public void build() {
        preview();
        this.clearPlaceholders();

        if (this.root == null) {
            return;
        }
        if (!this.schematic.cost.get(0).has(user.getPlayer())) {
            return;
        }
        this.schematic.cost.get(0).take(user.getPlayer());
        if (!canBuild()) {
            return;
        }

        if(!this.isFoundation()){
            if(Main.instance.databaseManager.getBuild(this.root.newUnOffset(this.schematic.rootOffset))==null){
                return;
            }
        }

        for (PluginLocation location : this.placeholders) {
            PluginLocation offset = location.newUnOffset(this.root);
            if (this.rotate) {
                offset.flip();
            }
            if(!this.schematic.offsets.containsKey(offset.toPosition())){
                return;
            }
            location.setBlock(this.schematic.offsets.get(offset.toPosition()).get(0).parseMaterial());
        }


        Main.instance.databaseManager.save(
                new Build(
                        this.user.id,
                        this.schematic.type,
                        this.schematic.type.equals("foundation") ? -1 : Main.instance.databaseManager.getBuild(this.root.newUnOffset(this.schematic.rootOffset)).id,
                        this.root,
                        this.placeholders,
                        this.collidingFoundations)
        );

        for (Build foundation : this.collidingFoundations) {
            foundation.addCollidingFoundation(Main.instance.databaseManager.getBuild(this.root));
        }
    }

    private boolean canBuild() {
        if (this.root.y >= Main.instance.config.maxY) {
            return false;
        }

        this.collidingFoundations = new ArrayList<>();

        //Get the target block
        Block target = Utils.getTargetBlock(user.getPlayer(), 8, false, true);

        if (isFoundation()) {
            this.canCollide = true;
            if (target.getType().equals(Material.AIR)) {
                PluginLocation check = new PluginLocation(target.getLocation()).newOffset(new Position(0, -2, 0));
                if (check.getBlock().getType().equals(Material.AIR)) {
                    return false;
                }
            }
        } else {
            if (target.getType().equals(Material.AIR)) {
                return false;
            }
        }

        if (new PluginLocation(target.getLocation()).toLocation().distance(user.getPlayer().getLocation()) <= 2) {
            return false;
        }

        for (Position position : this.schematic.offsets.keySet()) {
            Position offset = position.clone();
            if (this.rotate) {
                offset.flip();
            }
            PluginLocation location = this.root.newOffset(offset);

            if (this.canCollideWithFoundation) {
                Build b = Main.instance.databaseManager.getBuild(location);
                if (b != null && b.isFoundation()) {
                    collidingFoundations.add(b);
                    continue;
                }
            }

            Build b = Main.instance.databaseManager.getBuild(location);
            if (b != null) {
                if (this.schematic.type.equals(b.type)) {
                    continue;
                }
            }

            if (location.getBlock().getType().equals(Material.AIR)) {
                continue;
            }

            if (this.canCollide) {
                continue;
            }

            return false;
        }
        return true;
    }

}
