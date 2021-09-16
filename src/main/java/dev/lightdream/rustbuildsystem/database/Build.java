package dev.lightdream.rustbuildsystem.database;

import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.files.dto.Position;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustbuildsystem.Main;
import dev.lightdream.rustbuildsystem.files.dto.ConfigurablePluginLocation;
import dev.lightdream.rustbuildsystem.files.dto.ConfigurablePosition;
import dev.lightdream.rustbuildsystem.files.dto.Cost;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

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
    @DatabaseField(columnName = "root_location", dataType = DataType.SERIALIZABLE)
    public PluginLocation rootLocation;
    @DatabaseField(columnName = "block_locations", dataType = DataType.SERIALIZABLE)
    public HashSet<ConfigurablePluginLocation> blockLocations;
    @DatabaseField(columnName = "level")
    public int level;
    @DatabaseField(columnName = "health")
    public Integer health;
    @DatabaseField(columnName = "colliding", dataType = DataType.SERIALIZABLE)
    public HashSet<Integer> colliding;

    //private List<PluginLocation> blockLocationsList;

    public Build(int ownerId, String type, int foundationID, PluginLocation rootLocation, List<ConfigurablePluginLocation> blockLocations, List<Build> colliding) {
        this.ownerId = ownerId;
        this.type = type;
        this.foundationID = foundationID;
        //this.rootLocation = new Gson().toJson(rootLocation);
        this.rootLocation = rootLocation;
        //this.blockLocations = new Gson().toJson(blockLocations);
        this.blockLocations = new HashSet<>(blockLocations);
        this.level = 0;
        this.health = Main.instance.config.builds.get(type).getHeath().get(level);
        List<Integer> collidingIDs = new ArrayList<>();
        colliding.forEach(build -> collidingIDs.add(build.id));
        this.colliding = new HashSet<>(collidingIDs);
    }

    public boolean isFoundation() {
        return type.equals("foundation");
    }

    public List<PluginLocation> getMarinRoots(boolean fullRotations, boolean corners, boolean foundationPlace) {
                /*

        if (isRoof() || isFoundation()) {
        } else {
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
                minX = (int) Math.floor(location.x);
            }
            if (minZ >= location.z) {
                minZ = (int) Math.floor(location.z);
            }
            if (maxX <= location.x) {
                maxX = (int) Math.floor(location.x);
            }
            if (maxZ <= location.z) {
                maxZ = (int) Math.floor(location.z);
            }
            if (maxY <= location.y) {
                maxY = (int) Math.floor(location.y);
            }
        }
        */

        if (!fullRotations) {
            if (!foundationPlace) {
                return Arrays.asList(
                    /*
                    new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2),
                    new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2),
                    new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 90, 0),
                    new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0)
                    */
                        this.rootLocation.newOffset(-2, 0, 0).newRotation(0, 0),
                        this.rootLocation.newOffset(2, 0, 0).newRotation(0, 0),
                        this.rootLocation.newOffset(0, 0, -2).newRotation(90, 0),
                        this.rootLocation.newOffset(0, 0, 2).newRotation(90, 0)
                );
            } else {
                return Arrays.asList(
                        this.rootLocation.newOffset(-4, 0, 0).newRotation(0, 0),
                        this.rootLocation.newOffset(4, 0, 0).newRotation(0, 0),
                        this.rootLocation.newOffset(0, 0, -4).newRotation(90, 0),
                        this.rootLocation.newOffset(0, 0, 4).newRotation(90, 0)
                );
            }
        }
        if (!corners) {
            if (!foundationPlace) {

                return Arrays.asList(
                        /*
                        new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2, 0, 0),
                        new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0),
                        new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2, 180, 0),
                        new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 270, 0)
                        */
                        this.rootLocation.newOffset(-2, 0, 0).newRotation(0, 0),
                        this.rootLocation.newOffset(2, 0, 0).newRotation(90, 0),
                        this.rootLocation.newOffset(0, 0, -2).newRotation(180, 0),
                        this.rootLocation.newOffset(0, 0, 2).newRotation(270, 0)
                );
            } else {
                return Arrays.asList(
                        this.rootLocation.newOffset(-4, 0, 0).newRotation(0, 0),
                        this.rootLocation.newOffset(4, 0, 0).newRotation(90, 0),
                        this.rootLocation.newOffset(0, 0, -4).newRotation(180, 0),
                        this.rootLocation.newOffset(0, 0, 4).newRotation(270, 0)
                );
            }
        }
        if (foundationPlace) {

            return Arrays.asList(
                /*
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, (minZ + maxZ) / 2, 0, 0),
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, maxZ, 45, 0),
                new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, maxZ, 90, 0),
                new PluginLocation(blockLocations.get(0).world, minX, maxY, maxZ, 135, 0),

                new PluginLocation(blockLocations.get(0).world, minX, maxY, (minZ + maxZ) / 2, 180, 0),
                new PluginLocation(blockLocations.get(0).world, minX, maxY, minZ, 225, 0),
                new PluginLocation(blockLocations.get(0).world, (minX + maxX) / 2, maxY, minZ, 270, 0),
                new PluginLocation(blockLocations.get(0).world, maxX, maxY, minZ, 315, 0)
                */
                    this.rootLocation.newOffset(2, 0, 0).newRotation(0, 0),
                    this.rootLocation.newOffset(2, 0, 2).newRotation(45, 0),
                    this.rootLocation.newOffset(0, 0, 2).newRotation(90, 0),
                    this.rootLocation.newOffset(-2, 0, 2).newRotation(135, 0),

                    this.rootLocation.newOffset(-2, 0, 0).newRotation(180, 0),
                    this.rootLocation.newOffset(-2, 0, -2).newRotation(225, 0),
                    this.rootLocation.newOffset(0, 0, -2).newRotation(270, 0),
                    this.rootLocation.newOffset(2, 0, -2).newRotation(315, 0)

            );
        } else {
            return Arrays.asList(
                    this.rootLocation.newOffset(4, 0, 0).newRotation(0, 0),
                    this.rootLocation.newOffset(4, 0, 4).newRotation(45, 0),
                    this.rootLocation.newOffset(0, 0, 4).newRotation(90, 0),
                    this.rootLocation.newOffset(-4, 0, 4).newRotation(135, 0),

                    this.rootLocation.newOffset(-4, 0, 0).newRotation(180, 0),
                    this.rootLocation.newOffset(-4, 0, -4).newRotation(225, 0),
                    this.rootLocation.newOffset(0, 0, -4).newRotation(270, 0),
                    this.rootLocation.newOffset(4, 0, -4).newRotation(315, 0)
            );
        }

    }

    public PluginLocation getClosestMarginRoot(PluginLocation targetLocation, boolean fullRotate, boolean corners, boolean newFoundation) {
        PluginLocation location = null;
        double minDistance = 1000000000;
        for (PluginLocation marginRoot : this.getMarinRoots(fullRotate, corners, newFoundation)) {
            double distance = targetLocation.toLocation().distance(marginRoot.toLocation());
            if (minDistance > distance) {
                minDistance = distance;
                location = marginRoot;
            }
        }
        return location;
    }

    public List<ConfigurablePluginLocation> getBlockLocations() {
        //if (blockLocationsList == null) {
        //    return Arrays.asList(new Gson().fromJson(blockLocations, PluginLocation[].class));
        //}
        //return blockLocationsList;
        return new ArrayList<>(blockLocations);
    }

    public PluginLocation getRootLocation() {
        return this.rootLocation;
        //return new Gson().fromJson(this.rootLocation, PluginLocation.class);
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
        if (isFoundation() || isRoof()) {
            Main.instance.databaseManager.getBuilds(this.id).forEach(Build::destroy);
        }

        this.getBlockLocations().forEach(location -> location.setBlock(Material.AIR));

        List<Build> arrayList = new ArrayList<>();
        arrayList.add(this);
        getColliding().forEach(build -> build.rebuild(arrayList));

        Main.instance.databaseManager.delete(this);

        if (isWall()) {
            List<Build> walls = Main.instance.databaseManager.getBuilds(foundationID, "wall");
            if (walls.size() == 0) {
                Main.instance.databaseManager.getBuilds(foundationID).forEach(Build::destroy);
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
        this.health = Main.instance.config.builds.get(this.type).getHeath().get(this.level);
        Cost cost = Main.instance.config.builds.get(this.type).getCost().get(this.level);
        Player player = Main.instance.databaseManager.getUser(this.ownerId).getPlayer();
        if (!cost.has(player)) {
            this.level--;
            this.health = Main.instance.config.builds.get(this.type).getHeath().get(this.level);
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

    public void rebuild(List<Build> rebuilt) {
        List<Build> builds;
        if (isFoundation() || isRoof()) {
            builds = Main.instance.databaseManager.getBuilds(this.id);
        } else {
            builds = Main.instance.databaseManager.getBuilds(foundationID);
        }

        builds.remove(this);

        for (Build build : builds) {
            if (rebuilt.contains(build)) {
                continue;
            }
            rebuilt.add(build);
            build.rebuild(rebuilt);
        }

        build();
    }

    public void build() {
        for (ConfigurablePosition p : Main.instance.config.builds.get(this.type).getOffsets().keySet()) {
            Position offset = p.clone();
            if (this.rootLocation.rotationX == 90) {
                offset.flip();
            }
            getRootLocation().newOffset(offset).setBlock(Main.instance.config.builds.get(this.type).getOffsets().get(p).get(this.level).parseMaterial());
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

    public List<Build> getColliding() {
        List<Build> collidingFoundations = new ArrayList<>();
        for (Integer id : new ArrayList<>(this.colliding)) {
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
        colliding.add(build.id);
        Main.instance.databaseManager.save(this);
    }
}
