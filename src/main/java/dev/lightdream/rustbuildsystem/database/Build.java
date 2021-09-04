package dev.lightdream.rustbuildsystem.database;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.rustbuildsystem.Main;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DatabaseTable(tableName = "builds")
@NoArgsConstructor
public class Build {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "owner_id")
    public int ownerId;
    @DatabaseField(columnName = "type")
    public String type;
    @DatabaseField(columnName = "foundation_id")
    public int foundationID;
    @DatabaseField(columnName = "root_location")
    public String rootLocation;
    @DatabaseField(columnName = "block_locations")
    public String blockLocations;
    @DatabaseField(columnName = "level")
    public int level;

    public Build(int ownerId, String type, int foundationID, PluginLocation rootLocation, List<PluginLocation> blockLocations) {
        this.ownerId = ownerId;
        this.type = type;
        this.foundationID = foundationID;
        this.rootLocation = new Gson().toJson(rootLocation);
        this.blockLocations = new Gson().toJson(blockLocations);
        this.level = 0;
    }

    public boolean isFoundation() {
        return type.equals("foundation") || type.equals("roof");
    }

    public List<PluginLocation> getWallRoots() {
        if (!isFoundation()) {
            return new ArrayList<>();
        }

        int minX = 1000000000;
        int minZ = 1000000000;
        int maxX = -1000000000;
        int maxZ = -1000000000;
        int maxY = -1000000000;
        List<PluginLocation> blockLocations = getBlockLocations();
        for (PluginLocation location : blockLocations) {
            if (minX >= location.x) {
                minX = (int) location.x;
            }
            if (minZ >= location.z) {
                minZ = (int) location.z;
            }
            if (maxX <= location.x) {
                maxX = (int) location.x;
            }
            if (maxZ <= location.z) {
                maxZ = (int) location.z;
            }
            if (maxY <= location.y) {
                maxY = (int) location.y;
            }
        }
        return Arrays.asList(
                new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2),
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2),
                new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 90, 0),
                new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0)
        );
    }

    public List<PluginLocation> getBlockLocations() {
        return Arrays.asList(new Gson().fromJson(blockLocations, PluginLocation[].class));
    }

    public PluginLocation getRootLocation() {
        return new Gson().fromJson(this.rootLocation, PluginLocation.class);
    }

    public Build getFoundation() {
        if (isFoundation()) {
            return this;
        }
        return Main.instance.databaseManager.getBuild(foundationID);
    }

    public void destroy() {
        if (isFoundation()) {
            Main.instance.databaseManager.getBuilds(this.id).forEach(Build::destroy);
        }

        this.getBlockLocations().forEach(location -> {
            location.setBlock(Material.AIR);
        });
        Main.instance.databaseManager.delete(this);
    }


}
