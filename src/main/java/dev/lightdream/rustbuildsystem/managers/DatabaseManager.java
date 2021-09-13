package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.rustbuildsystem.database.Build;
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

    public @Nullable Build getBuild(PluginLocation location) {
        Optional<Build> optionalBuild = getAll(Build.class).stream().filter(build -> build.getBlockLocations().contains(location)).findFirst();

        return optionalBuild.orElse(null);
    }

    public @Nullable Build getBuild(int id) {
        Optional<Build> optionalBuild = getAll(Build.class).stream().filter(build -> build.id == id).findFirst();

        return optionalBuild.orElse(null);
    }
}
