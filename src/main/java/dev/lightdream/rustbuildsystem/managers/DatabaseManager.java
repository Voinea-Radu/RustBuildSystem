package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.managers.local.LocalDatabaseManager;
import dev.lightdream.rustbuildsystem.database.Build;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DatabaseManager extends LocalDatabaseManager {
    public DatabaseManager(LightDreamPlugin plugin) {
        super(plugin);

        createTable(Build.class);
        createDao(Build.class);
    }

    @SneakyThrows
    public @NotNull List<Build> getBuilds() {
        return (List<Build>) getDao(Build.class).queryForAll();
    }

    public @NotNull List<Build> getBuilds(int foundationID) {
        return getBuilds().stream().filter(build -> build.foundationID == foundationID).collect(Collectors.toList());
    }

    public @Nullable Build getBuild(PluginLocation location) {
        Optional<Build> optionalBuild = getBuilds().stream().filter(build -> build.getBlockLocations().contains(location)).findFirst();

        return optionalBuild.orElse(null);
    }

    public @Nullable Build getBuild(int id) {
        Optional<Build> optionalBuild = getBuilds().stream().filter(build -> build.id == id).findFirst();

        return optionalBuild.orElse(null);
    }


}
