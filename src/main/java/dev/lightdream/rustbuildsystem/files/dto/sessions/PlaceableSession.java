package dev.lightdream.rustbuildsystem.files.dto.sessions;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.Utils;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.BuildSchematic;
import dev.lightdream.rustbuildsystem.files.dto.BuildSession;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class PlaceableSession extends BuildSession {

    public PlaceableSession(User user, BuildSchematic schematic) {
        super(user, schematic);
    }

    @Override
    public void preview() {
        Block target = Utils.getTargetBlock(user.getPlayer(), 8, false, false);
        this.targetBuild = null;

        if(target == null){
            System.out.println(1);
            return;
        }

        if (this.root != null) {
            if (this.root.equals(new PluginLocation(target.getLocation()))) {
                System.out.println(2);
                return;
            }
        }

        this.root = new PluginLocation(target.getLocation());
        clearPlaceholders();
        this.placeholders = new HashMap<>();

        if(this.root == null){
            System.out.println(3);
            return;
        }

        this.targetBuild = Main.instance.databaseManager.getBuild(this.root);

        if (targetBuild == null) {
            System.out.println(4);
            return;
        }

        Build foundation = targetBuild.getFoundation();

        if (foundation == null) {
            System.out.println(5);
            return;
        }

        if (this.schematic.isRoof()) {
            if (Main.instance.databaseManager.getBuilds(foundation.id, "wall").size() == 0) {
                System.out.println(6);
                return;
            }
        }

        this.root = targetBuild.getFoundation().getRootLocation();

        if (this.root == null) {
            System.out.println(7);
            return;
        }

        this.root = this.root.newOffset(this.schematic.rootOffset);

        if (Main.instance.databaseManager.getBuild(this.root) != null) {
            System.out.println(8);
            return;
        }

        boolean canBuild = canBuild();
        System.out.println(canBuild);
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

            if (location.getBlock().getType().equals(Material.AIR)) {
                continue;
            }

            canBuild = false;
        }

        return canBuild;
    }
}
