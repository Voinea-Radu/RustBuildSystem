package dev.lightdream.rustbuildsystem.files.dto.sessions;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class FoundationSession extends BuildSession {

    public FoundationSession(User user, BuildSchematic schematic) {
        super(user, schematic);
    }

    @Override
    public void preview() {
        //Get the target block
        Block target = Utils.getTargetBlock(user.getPlayer(), 8, false, true);
        this.targetBuild = null;

        if (this.root != null) {
            if (this.root.equals(new PluginLocation(target.getLocation()))) {
                return;
            }
        }

        if(target == null){
            return;
        }

        this.root = new PluginLocation(target.getLocation());
            clearPlaceholders();
            this.placeholders = new HashMap<>();

            if(root== null){
                return;
            }

        this.targetBuild = Main.instance.databaseManager.getBuild(this.root);


        if (target.getType().equals(Material.AIR)) {
            if (this.root.newOffset(new Position(0, -2, 0)).getBlock().getType().equals(Material.AIR)) {
                this.root=null;
                return;
            }
        }

        if (this.root.y >= Main.instance.config.maxY ||
                this.root.toLocation().distance(user.getPlayer().getLocation()) <= Main.instance.config.minBuildOnGroundDistance) {
            return;
        }

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
                for (int i = (int) (this.root.y - 3); i <= this.root.y + 3; i++) {
                    check.y = (double) i;
                    if (Main.instance.databaseManager.getBuild(check) != null) {
                        return;
                    }
                }
            }
        } else {
            if (this.targetBuild.isFoundation() || this.targetBuild.isRoof()) {
                this.root = this.targetBuild.getClosestMarginRoot(this.root, true, true);
                //this.canCollideWithFoundation = true;
                switch ((int) this.root.rotationX) {
                    case 0:
                        this.root.offset(2, 0, 0);
                        break;
                    case 45:
                        this.root.offset(2, 0, 2);
                        break;
                    case 90:
                        this.root.offset(0, 0, 2);
                        break;
                    case 135:
                        this.root.offset(-2, 0, 2);
                        break;
                    case 180:
                        this.root.offset(-2, 0, 0);
                        break;
                    case 225:
                        this.root.offset(-2, 0, -2);
                        break;
                    case 270:
                        this.root.offset(0, 0, -2);
                        break;
                    case 315:
                        this.root.offset(2, 0, -2);
                        break;
                }
                this.root.unOffset(schematic.rootOffset);
            }
        }
        if (this.root == null) {
            return;
        }

        this.root = this.root.newOffset(this.schematic.rootOffset);

        if (Main.instance.databaseManager.getBuild(this.root) != null) {
            return;
        }

        boolean canBuild = canBuild();
        showPreview(canBuild);
        if (!canBuild) {
            this.root = null;
        }
    }

    @Override
    public boolean canBuild() {
        boolean canBuild = true;

        for (Position position : this.schematic.offsets.keySet()) {
            PluginLocation location = this.root.newOffset(position);
            placeholders.put(location, schematic.offsets.get(position).get(0));

            if (!canBuild) {
                continue;
            }

            Build b = Main.instance.databaseManager.getBuild(location);
            if (b != null) {
                if (this.schematic.type.equals(b.type)) {
                    continue;
                }
            }

            if (position.y <= this.schematic.canCollideUnder) {
                continue;
            }

            if (location.getBlock().getType().equals(Material.AIR)) {
                continue;
            }

            canBuild = false;
        }

        return canBuild;
    }
}
