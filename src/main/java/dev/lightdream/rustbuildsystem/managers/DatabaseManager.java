package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.rustbuildsystem.database.Build;
import dev.lightdream.rustbuildsystem.files.dto.ConfigurablePluginLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DatabaseManager extends dev.lightdream.api.managers.DatabaseManager {

    public DatabaseManager(LightDreamPlugin plugin) {
        super(plugin);

        setup(User.class);
        setup(Build.class);
    }


    public @NotNull List<Build> getBuilds(int foundationID) {
        return getAll(Build.class).stream().filter(build -> build.foundationID == foundationID).collect(Collectors.toList());
    }

    public @NotNull List<Build> getBuilds(int foundationID, String type) {
        return getAll(Build.class).stream().filter(build -> build.foundationID == foundationID && build.type.equals(type)).collect(Collectors.toList());
    }

    public @Nullable Build getBuild(PluginLocation location, boolean breakable) {
        Optional<Build> optionalBuild;
        if (breakable) {
            optionalBuild = getAll(Build.class).stream().filter(build -> build.getBlockLocations().contains(new ConfigurablePluginLocation(location, true))).findFirst();
        } else {
            optionalBuild = getAll(Build.class).stream().filter(build -> build.getBlockLocations().contains(new ConfigurablePluginLocation(location, true)) ||
                    build.getBlockLocations().contains(new ConfigurablePluginLocation(location, false))).findFirst();
        }

        return optionalBuild.orElse(null);
    }

    public @Nullable Build getBuild(PluginLocation location) {
        return getBuild(location, false);
    }

    public @Nullable Build getBuild(int id) {
        Optional<Build> optionalBuild = getAll(Build.class).stream().filter(build -> build.id == id).findFirst();

        return optionalBuild.orElse(null);
    }
}


/*

[17:51:59 INFO]: Build{id=1, ownerId=0, type='foundation', foundationID=-1, rootLocation='PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-40.0, y=64.0, z=-5.0}', blockLocations='[PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-41.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-39.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=63.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-41.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=63.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-39.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-40.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-40.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=63.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=63.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-40.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-40.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-42.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-40.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-38.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-41.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-39.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-41.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-39.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-41.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-39.0, y=64.0, z=-3.0}]', level=0}
[17:51:59 INFO]: Build{id=2, ownerId=0, type='foundation', foundationID=-1, rootLocation='PluginLocation{world='world', rotationX=90.0,rotationY=0.0, x=-40.0, y=64.0, z=-1.0}', blockLocations='[PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-42.0, y=64.0, z=0.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-42.0, y=64.0, z=-2.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-42.0, y=63.0, z=1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-40.0, y=64.0, z=-2.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-40.0, y=64.0, z=0.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-41.0, y=64.0, z=-2.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-41.0, y=64.0, z=0.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-38.0, y=64.0, z=0.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-38.0, y=64.0, z=-2.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-38.0, y=63.0, z=1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-39.0, y=64.0, z=-2.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-39.0, y=64.0, z=0.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-42.0, y=64.0, z=1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-42.0, y=64.0, z=-1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-41.0, y=64.0, z=-1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-41.0, y=64.0, z=1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-40.0, y=64.0, z=-1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-40.0, y=64.0, z=1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-39.0, y=64.0, z=1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-39.0, y=64.0, z=-1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-38.0, y=64.0, z=-1.0}, PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-38.0, y=64.0, z=1.0}]', level=0}
[17:51:59 INFO]: Build{id=4, ownerId=0, type='foundation', foundationID=-1, rootLocation='PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-5.0}', blockLocations='[PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=63.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=63.0, z=-7.0}]', level=0}
PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-3.0}
PluginLocation{world='world', rotationX=90.0, rotationY=0.0, x=-35.0, y=64.0, z=-3.0}
Build{id=4, ownerId=0, type='foundation', foundationID=-1, rootLocation='PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-5.0}', blockLocations='[PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-7.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=63.0, z=-3.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-6.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-5.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-36.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-37.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-35.0, y=64.0, z=-4.0}, PluginLocation{world='world', rotationX=0.0, rotationY=0.0, x=-34.0, y=63.0, z=-7.0}]', level=0}

 */
