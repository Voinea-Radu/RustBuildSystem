package dev.lightdream.rustbuildsystem.database;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    @DatabaseField(columnName = "health")
    public Integer health;
    @DatabaseField(columnName = "colliding_foundations")
    public String collidingFoundations;

    public Build(int ownerId, String type, int foundationID, PluginLocation rootLocation, List<PluginLocation> blockLocations, List<Build> collidingFoundations) {
        this.ownerId = ownerId;
        this.type = type;
        this.foundationID = foundationID;
        this.rootLocation = new Gson().toJson(rootLocation);
        this.blockLocations = new Gson().toJson(blockLocations);
        this.level = 0;
        this.health = Main.instance.config.builds.get(type).heath.get(level);
        List<Integer> collidingFoundationsIDs = new ArrayList<>();
        collidingFoundations.forEach(build -> collidingFoundationsIDs.add(build.id));
        this.collidingFoundations = new Gson().toJson(collidingFoundationsIDs);
    }

    public boolean isFoundation() {
        return type.equals("foundation");
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public List<PluginLocation> getMarinRoots(boolean fullRotations, boolean corners) {
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
        if (!fullRotations) {
            return Arrays.asList(
                    new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2),
                    new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2),
                    new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 90, 0),
                    new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0)
            );
        }
        if (!corners) {
            return Arrays.asList(
                    new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2, 0, 0),
                    new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0),
                    new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2, 180, 0),
                    new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 270, 0)
            );
        }
        return Arrays.asList(
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2, 0, 0),
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, maxZ, 45, 0),
                new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0),
                new PluginLocation(blockLocations.get(0).world, minX, maxY, maxZ, 135, 0),
                new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2, 180, 0),
                new PluginLocation(blockLocations.get(0).world, minX, maxY, minZ, 225, 0),
                new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 270, 0),
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, minZ, 315, 0)
        );
    }

    public PluginLocation getClosestMarginRoot(PluginLocation targetLocation, boolean fullRotate, boolean corners) {
        PluginLocation location = null;
        double minDistance = 1000000000;
        for (PluginLocation marginRoot : this.getMarinRoots(fullRotate, corners)) {
            double distance = targetLocation.toLocation().distance(marginRoot.toLocation());
            if (minDistance > distance) {
                minDistance = distance;
                location = marginRoot;
            }
        }
        return location;
    }

    public List<PluginLocation> getBlockLocations() {
        return Arrays.asList(new Gson().fromJson(blockLocations, PluginLocation[].class));
    }

    public PluginLocation getRootLocation() {
        return new Gson().fromJson(this.rootLocation, PluginLocation.class);
    }

    public Build getFoundation() {
        if (isFoundation() || isRoof()) {
            return this;
        }
        return Main.instance.databaseManager.getBuild(foundationID);
    }

    public List<Build> getWalls() {
        if (isFoundation() || isRoof()) {
            return Main.instance.databaseManager.getBuilds(this.id, "wall");
        }
        return new ArrayList<>();
    }

    public boolean isRoof() {
        return this.type.equals("roof");
    }

    public void destroy() {
        if (isFoundation()) {
            Main.instance.databaseManager.getBuilds(this.id).forEach(build -> destroy());
        }

        this.getBlockLocations().forEach(location -> {
            location.setBlock(Material.AIR);
        });

        getCollidingFoundations().forEach(build -> {
            build.rebuild();
        });

        Main.instance.databaseManager.delete(this);

        if (isWall()) {
            List<Build> walls = Main.instance.databaseManager.getBuilds(foundationID, "wall");
            if (walls.size() == 0) {
                Main.instance.databaseManager.getBuilds(foundationID).forEach(build -> destroy());
            }
        }
    }

    @Override
    public String toString() {
        return "Build{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", type='" + type + '\'' +
                ", foundationID=" + foundationID +
                ", rootLocation='" + rootLocation + '\'' +
                ", blockLocations='" + blockLocations + '\'' +
                ", level=" + level +
                '}';
    }

    public boolean isWall() {
        return type.equals("wall");
    }

    public void upgrade() {
        this.level++;
        this.health = Main.instance.config.builds.get(this.type).heath.get(this.level);
        Cost cost = Main.instance.config.builds.get(this.type).cost.get(this.level);
        Player player = Main.instance.databaseManager.getUser(this.ownerId).getPlayer();
        if (!cost.has(player)) {
            this.level--;
            this.health = Main.instance.config.builds.get(this.type).heath.get(this.level);
            return;
        }

        cost.take(player);

        build();
    }

    public void damage() {
        this.health--;
        if (this.health <= 0) {
            destroy();
        } else {
            Main.instance.databaseManager.save(this);
        }
    }

    public void rebuild() {
        List<Build> builds;
        if (isFoundation() || isRoof()) {
            builds = Main.instance.databaseManager.getBuilds(this.id);
        } else {
            builds = Main.instance.databaseManager.getBuilds(foundationID);
        }

        builds.remove(this);

        for (Build build : builds) {
            build.rebuild();
        }

        build();
    }

    public void build() {
        for (Position offset : Main.instance.config.builds.get(this.type).offsets.keySet()) {
            PluginLocation location = getRootLocation().newOffset(offset);
            location.setBlock(Main.instance.config.builds.get(this.type).offsets.get(offset).get(this.level).parseMaterial());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Build build = (Build) o;
        return id == build.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<Build> getCollidingFoundations() {
        List<Build> collidingFoundations = new ArrayList<>();
        for (Integer id : new Gson().fromJson(this.collidingFoundations, Integer[].class)) {
            Build build = Main.instance.databaseManager.getBuild(id);
            if (build == null) {
                continue;
            }
            collidingFoundations.add(build);
        }
        return collidingFoundations;
    }

    public void addCollidingFoundation(Build build) {
        if (build == null) {
            return;
        }
        List<Integer> collidingFoundationsID = new ArrayList<>(Arrays.asList(new Gson().fromJson(this.collidingFoundations, Integer[].class)));
        collidingFoundationsID.add(build.id);
        this.collidingFoundations = new Gson().toJson(collidingFoundationsID);
        Main.instance.databaseManager.save(this);
    }
}
