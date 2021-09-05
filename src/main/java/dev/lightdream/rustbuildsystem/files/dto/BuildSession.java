package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BuildSession {

    public BuildSchematic schematic;
    public List<PluginLocation> placeholderBlocks;
    public PluginLocation root;
    public PluginLocation buildTarget;
    public boolean canBuild;
    public boolean rotate;
    public boolean canCollideWithFoundation;

    public BuildSession(BuildSchematic schematic) {
        this.schematic = schematic;
        placeholderBlocks = new ArrayList<>();
        root = null;
        canBuild = false;
    }

    public void clearPlaceholders(Player player) {
        placeholderBlocks.forEach(location -> {
            //player.sendBlockChange(location.toLocation(), Material.AIR, XMaterial.AIR.getData());
            player.sendBlockChange(location.toLocation(), location.getBlock().getType(), location.getBlock().getData());
        });
    }

    public boolean isFoundation() {
        return schematic.type.equals("foundation") || schematic.type.equals("roof");
    }

}
