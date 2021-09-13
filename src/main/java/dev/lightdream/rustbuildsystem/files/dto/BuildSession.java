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
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings({"deprecation"})
@AllArgsConstructor
@NoArgsConstructor
public abstract class BuildSession {

    public User user;
    public BuildSchematic schematic;
    public HashMap<PluginLocation, XMaterial> placeholders;
    public PluginLocation root;
    public boolean rotate;
    public Build targetBuild;
    public List<Build> collidingFoundations;
    public PluginLocation lastPreviewLocation;

    public BuildSession(User user, BuildSchematic schematic) {
        this.user = user;
        this.schematic = schematic;
        this.placeholders = new HashMap<>();
        this.root = null;
        this.rotate = false;
        //this.canCollideWithFoundation = false;
        this.targetBuild = null;
        this.collidingFoundations = new ArrayList<>();
        //this.canCollide = false;
        this.lastPreviewLocation = null;
    }

    public void clearPlaceholders() {
        placeholders.forEach((location, material) -> user.getPlayer().sendBlockChange(location.toLocation(), location.getBlock().getType(), location.getBlock().getData()));
    }

    public abstract void preview();

    public void build() {
        preview();
        this.clearPlaceholders();

        if (this.root == null) {
            System.out.println(1);
            return;
        }
        if (!this.schematic.getCost().get(0).has(user.getPlayer())) {
            System.out.println(2);
            return;
        }
        this.schematic.getCost().get(0).take(user.getPlayer());
        if (!canBuild()) {
            System.out.println(3);
            return;
        }
        showPreview(true);

        if (!this.schematic.isFoundation()) {
            if (targetBuild == null) {
                System.out.println(4);
                return;
            }
        }

        this.placeholders.forEach((location, material) -> {
            location.setBlock(material.parseMaterial());
        });
        Build build = new Build(
                this.user.id,
                this.schematic.getType(),
                this.schematic.getType().equals("foundation") ? -1 : targetBuild.id,
                this.root,
                new ArrayList<>(this.placeholders.keySet()),
                this.collidingFoundations);

        Main.instance.databaseManager.save(build);
        Main.instance.databaseManager.save(build, false);


        for (Build foundation : this.collidingFoundations) {
            foundation.addCollidingFoundation(Main.instance.databaseManager.getBuild(this.root));
        }
    }

    public abstract boolean canBuild();

    @SuppressWarnings("ConstantConditions")
    public void showPreview(boolean canBuild){
        List<PluginLocation> toRemove = new ArrayList<>();
        placeholders.forEach((location, material) -> {
            if (!location.getBlock().getType().equals(Material.AIR)) {
                toRemove.add(location);
                return;
            }

            user.getPlayer().sendBlockChange(location.toLocation(), canBuild ? XMaterial.LIME_STAINED_GLASS.parseMaterial() : XMaterial.RED_STAINED_GLASS.parseMaterial(),
                    canBuild ? XMaterial.LIME_STAINED_GLASS.getData() : XMaterial.LIME_STAINED_GLASS.getData());
        });
        toRemove.forEach(l->placeholders.remove(l));
    }
}
