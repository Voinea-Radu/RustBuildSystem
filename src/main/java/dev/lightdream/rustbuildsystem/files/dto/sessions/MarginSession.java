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

import java.util.HashMap;

public class MarginSession extends BuildSession {

    public MarginSession(User user, BuildSchematic schematic) {
        super(user, schematic);
    }

    @Override
    public void preview() {
        Block target = Utils.getTargetBlock(user.getPlayer(), Main.instance.config.range, false, false);
        this.targetBuild = null;

        if (target == null) {
            return;
        }

        if (this.root != null) {
            if (this.root.equals(new PluginLocation(target.getLocation()))) {
                return;
            }
        }

        this.root = new PluginLocation(target.getLocation());
        clearPlaceholders();
        this.placeholders = new HashMap<>();

        if (this.root == null) {
            return;
        }

        this.targetBuild = Main.instance.databaseManager.getBuild(this.root);

        if (targetBuild == null) {
            return;
        }

        if (!targetBuild.isFoundation() && !targetBuild.isRoof()) {
            this.root = null;
            return;
        }

        this.root = this.targetBuild.getClosestMarginRoot(this.root, false, false, false);

        if (this.root == null) {
            return;
        }

        this.root = this.root.newOffset(this.schematic.getRootOffset());
        this.rotate = this.root.rotationX == 90;

        if (Main.instance.databaseManager.getBuild(root, 0) != null) {
            this.root = null;
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

        for (ConfigurablePosition position : this.schematic.getOffsets().keySet()) {
            Position offset = position.clone();
            if (this.rotate) {
                offset.flip();
            }
            PluginLocation location = this.root.newOffset(offset);
            placeholders.put(new ConfigurablePluginLocation(location, position.breakable), schematic.getOffsets().get(position).get(0));

            Build b = Main.instance.databaseManager.getBuild(location);
            if (b != null) {
                if (b.isRoof()) {
                    continue;
                }
                if (b.isWall()) {
                    continue;
                }
            }

            if (location.getBlock().getType().equals(Material.AIR)) {
                continue;
            }

            canBuild = false;
        }

        return canBuild;
    }
}
