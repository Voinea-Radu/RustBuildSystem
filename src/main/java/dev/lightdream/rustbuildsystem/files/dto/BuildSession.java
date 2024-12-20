package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.XMaterial;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.database.Build;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings({"deprecation"})
@AllArgsConstructor
@NoArgsConstructor
public abstract class BuildSession {

    public User user;
    public BuildSchematic schematic;
    public HashMap<ConfigurablePluginLocation, XMaterial> placeholders;
    public PluginLocation root;
    public boolean rotate;
    public Build targetBuild;
    public List<Build> colliding;
    public PluginLocation lastPreviewLocation;

    public BuildSession(User user, BuildSchematic schematic) {
        this.user = user;
        this.schematic = schematic;
        this.placeholders = new HashMap<>();
        this.root = null;
        this.rotate = false;
        this.targetBuild = null;
        this.colliding = new ArrayList<>();
        this.lastPreviewLocation = null;
    }

    public void clearPlaceholders() {
        placeholders.forEach((location, material) -> user.getPlayer().sendBlockChange(location.toLocation(), location.getBlock().getType(), location.getBlock().getData()));
    }

    public abstract void preview();

    public void build(Player player) {
        preview();
        this.clearPlaceholders();

        if (this.root == null) {
            return;
        }
        if (!this.schematic.getCost().get(0).has(user.getPlayer())) {
            return;
        }
        this.schematic.getCost().get(0).take(user.getPlayer());
        if (!canBuild()) {
            return;
        }
        showPreview(true);

        if (!this.schematic.isFoundation()) {
            if (targetBuild == null) {
                return;
            }
        }

        Bukkit.getOnlinePlayers().forEach(players -> {
            if (players.getLocation().distance(player.getLocation()) < 10) {
                player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_METAL, 1, 500);
            }
        });

        List<ConfigurablePluginLocation> toRemove = new ArrayList<>();

        HashSet<Build> hColliding = new HashSet<>(this.colliding);

        this.placeholders.forEach((l, m) -> {
            Build b = Main.instance.databaseManager.getBuild(l);
            if (b == null && !l.getBlock().getType().equals(Material.AIR)) {
                toRemove.add(l);
            }
            if (b != null) {
                hColliding.add(b);
            }
        });

        toRemove.forEach(l -> this.placeholders.remove(l));

        Build build = new Build(
                this.user.id,
                this.schematic.getType(),
                this.schematic.getName(),
                this.schematic.getType().equals("foundation") ? -1 : targetBuild.getFoundation().id,
                this.root,
                new ArrayList<>(this.placeholders.keySet()),
                new ArrayList<>(hColliding));

        Main.instance.databaseManager.save(build);
        build.build();

        for (Build foundation : hColliding) {
            foundation.addCollidingFoundation(Main.instance.databaseManager.getBuild(this.root));
        }

        Main.instance.databaseManager.save(build);
    }

    public abstract boolean canBuild();

    @SuppressWarnings("ConstantConditions")
    public void showPreview(boolean canBuild) {
        placeholders.forEach((location, material) -> {
            if (!location.getBlock().getType().equals(Material.AIR)) {
                Build b = Main.instance.getDatabaseManager().getBuild(location);
                if (b != null) {
                    if (!colliding.contains(b)) {
                        colliding.add(b);
                    }
                }
                return;
            }
            user.getPlayer().sendBlockChange(location.toLocation(), canBuild ? XMaterial.LIGHT_BLUE_STAINED_GLASS.parseMaterial() : XMaterial.RED_STAINED_GLASS.parseMaterial(),
                    canBuild ? XMaterial.LIGHT_BLUE_STAINED_GLASS.getData() : XMaterial.RED_STAINED_GLASS.getData());
        });
    }
}
