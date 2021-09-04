package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.files.dto.PluginLocation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BuildSession {

    public BuildSchematic schematic;
    public List<PluginLocation> placeholderBlocks;
    public PluginLocation root;
    public boolean canBuild;
    public boolean rotate;

    public BuildSession(BuildSchematic schematic) {
        this.schematic = schematic;
        placeholderBlocks = new ArrayList<>();
        root = null;
        canBuild = false;
    }

    public void clearPlaceholders(){
        placeholderBlocks.forEach(location -> location.setBlock(Material.AIR));
    }

}
