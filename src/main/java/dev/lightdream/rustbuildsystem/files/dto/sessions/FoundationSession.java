package dev.lightdream.rustbuildsystem.files.dto.sessions;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import dev.lightdream.rustbuildsystem.files.dto.ConfigurablePluginLocation;
import dev.lightdream.rustbuildsystem.files.dto.ConfigurablePosition;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
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
        Block target = Utils.getTargetBlock(user.getPlayer(), Main.instance.config.range, false, true);
        this.targetBuild = null;

        if (target == null) {
            return;
        }

        this.root = new PluginLocation(target.getLocation());
        clearPlaceholders();
        this.placeholders = new HashMap<>();
        this.colliding = new ArrayList<>();

        if (root == null) {
            return;
        }

        this.targetBuild = Main.instance.databaseManager.getBuild(this.root);

        if (target.getType().equals(Material.AIR)) {
            if (this.root.newOffset(new Position(0, -2, 0)).getBlock().getType().equals(Material.AIR)) {
                this.root = null;
                return;
            }
        }

        if (this.root.y >= Main.instance.config.maxY ||
                this.root.toLocation().distance(user.getPlayer().getLocation()) <= Main.instance.config.minBuildOnGroundDistance) {
            this.root = null;
            return;
        }

        if (this.targetBuild != null) {
            if (this.targetBuild.isFoundation()) {
                this.root = this.targetBuild.getClosestMarginRoot(this.root, true, false, true);
                if (this.root == null) {
                    return;
                }
                this.root.unOffset(schematic.getRootOffset());
            } else {
                this.root = null;
            }
        }
        if (this.root == null) {
            return;
        }

        this.root = this.root.newOffset(this.schematic.getRootOffset());

        if (Main.instance.databaseManager.getBuild(this.root) != null) {
            return;
        }

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
                Build b = Main.instance.databaseManager.getBuild(check);
                if (b != null) {
                    if (!b.equals(targetBuild)) {
                        if (targetBuild != null) {
                            if (targetBuild.getConnections().contains(b)) {
                                continue;
                            }
                        }

                        this.root = null;
                        return;
                    }
                }
            }
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

        for (ConfigurablePosition position : this.schematic.getOffsets().keySet()) {
            PluginLocation location = this.root.newOffset(position);
            placeholders.put(new ConfigurablePluginLocation(location, position.breakable), schematic.getOffsets().get(position).get(0));

            if (!canBuild) {
                continue;
            }

            Build b = Main.instance.databaseManager.getBuild(location);
            if (b != null) {
                if (this.schematic.getType().equals(b.type)) {
                    if (b.equals(targetBuild)) {
                        continue;
                    }
                    if (b.getConnections().contains(targetBuild)) {
                        continue;
                    }
                }
            }

            if (position.y <= this.schematic.getCanCollideUnder()) {
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
