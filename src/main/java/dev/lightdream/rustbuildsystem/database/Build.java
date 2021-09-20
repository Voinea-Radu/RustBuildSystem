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
    @DatabaseField(columnName = "name")
    public String name;
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

    public Build(int ownerId, String type, String name, int foundationID, PluginLocation rootLocation, List<ConfigurablePluginLocation> blockLocations, List<Build> colliding) {
        this.ownerId = ownerId;
        this.type = type;
        this.name = name;
        this.foundationID = foundationID;
        this.rootLocation = rootLocation;
        this.blockLocations = new HashSet<>(blockLocations);
        this.level = 0;
        this.health = Main.instance.config.builds.get(name).getHeath().get(level);
        List<Integer> collidingIDs = new ArrayList<>();
        colliding.forEach(build -> collidingIDs.add(build.id));
        this.colliding = new HashSet<>(collidingIDs);
    }

    public boolean isFoundation() {
        return type.equals("foundation");
    }

    public List<PluginLocation> getMarinRoots(boolean fullRotations, boolean corners, boolean foundationPlace) {

        if (!fullRotations) {
            if (!foundationPlace) {
                return Arrays.asList(
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
        return new ArrayList<>(blockLocations);
    }

    public PluginLocation getRootLocation() {
        return this.rootLocation;
    }

    public Build getFoundation() {
        if (isFoundation() || isRoof()) {
            return this;
        }
        return Main.instance.databaseManager.getBuild(foundationID);
    }

    public boolean isRoof() {
        return this.type.equals("roof");
    }

    @SuppressWarnings("ConstantConditions")
    public void destroy() {
        if (isFoundation() || isRoof()) {
            Main.instance.databaseManager.getBuilds(this.id).forEach(Build::destroy);
        }

        this.getBlockLocations().forEach(location -> location.setBlock(Material.AIR));

        HashSet<Build> hs = Main.instance.databaseManager.getBuild(getFoundation().id).getRebuilds();
        hs.remove(this);
        hs.forEach(Build::build);

        Main.instance.databaseManager.delete(this);

        if (isWall()) {
            List<Build> walls = Main.instance.databaseManager.getBuilds(foundationID, "wall");
            if (walls.size() == 0) {
                List<Build> roofs = Main.instance.databaseManager.getBuilds(foundationID, "roof");
                roofs.forEach(Build::destroy);
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

    @SuppressWarnings("ConstantConditions")
    public void upgrade() {
        this.level++;
        this.health = Main.instance.config.builds.get(this.name).getHeath().get(this.level);
        Cost cost = Main.instance.config.builds.get(this.name).getCost().get(this.level);
        Player player = Main.instance.databaseManager.getUser(this.ownerId).getPlayer();
        if (!cost.has(player)) {
            this.level--;
            this.health = Main.instance.config.builds.get(this.name).getHeath().get(this.level);
            return;
        }

        cost.take(player);

        build(true);
    }

    public void damage() {
        this.health--;
        if (this.health <= 0) {
            destroy();
        } else {
            Main.instance.databaseManager.save(this);
        }
    }

    public HashSet<Build> getRebuilds() {
        return getRebuilds(new HashSet<>());
    }

    public HashSet<Build> getRebuilds(HashSet<Build> rebuilt) {
        rebuilt.add(this);
        List<Build> builds = new ArrayList<>();
        builds.addAll(getConnections());
        builds.addAll(Main.instance.databaseManager.getBuilds(getFoundation().id));

        for (Build build : builds) {
            if (rebuilt.contains(build)) {
                continue;
            }
            rebuilt.add(build);
            build.getRebuilds(rebuilt);
        }

        return rebuilt;
    }

    public void build() {
        build(false);
    }

    @SuppressWarnings("deprecation")
    public void build(boolean force) {
        for (ConfigurablePosition p : Main.instance.config.builds.get(this.name).getOffsets().keySet()) {
            Position offset = p.clone();
            if (this.rootLocation.rotationX == 90) {
                offset.flip();
            }
            PluginLocation pluginLocation = getRootLocation().newOffset(offset);
            if (pluginLocation.getBlock().getType().equals(Material.AIR) || force) {
                pluginLocation.setBlock(Main.instance.config.builds.get(this.name).getOffsets().get(p).get(this.level).parseMaterial());
                pluginLocation.getBlock().setData(Main.instance.config.builds.get(this.name).getOffsets().get(p).get(this.level).getData());
            }
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

    public List<Build> getConnections() {
        return getConnections(new HashSet<>());
    }

    public List<Build> getConnections(HashSet<Build> current) {
        for (Integer id : new ArrayList<>(this.colliding)) {
            Build build = Main.instance.databaseManager.getBuild(id);
            if (build == null) {
                continue;
            }
            if (current.contains(build)) {
                continue;
            }
            current.add(build);
            current.addAll(build.getConnections(current));
        }
        return new ArrayList<>(current);
    }

    public void addCollidingFoundation(Build build) {
        if (build == null) {
            return;
        }
        colliding.add(build.id);
        Main.instance.databaseManager.save(this);
    }
}
