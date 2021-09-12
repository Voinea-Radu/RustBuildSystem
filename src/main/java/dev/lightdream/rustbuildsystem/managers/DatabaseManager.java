package dev.lightdream.rustbuildsystem.managers;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.libs.j256.dao.Dao;
import dev.lightdream.rustbuildsystem.database.Build;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DatabaseManager extends dev.lightdream.api.managers.DatabaseManager {

    public List<Build> builds= new ArrayList<>();

    public DatabaseManager(LightDreamPlugin plugin) {
        super(plugin);

        createTable(Build.class);
        createDao(Build.class);

        builds=getBuilds(true);
    }

    @SneakyThrows
    public @NotNull List<Build> getBuilds(boolean bypass) {
        if(bypass){
            return (List<Build>) getDao(Build.class).queryForAll();
        }else{
            return builds;
        }
    }

    public @NotNull List<Build> getBuilds(int foundationID) {
        return getBuilds(false).stream().filter(build -> build.foundationID == foundationID).collect(Collectors.toList());
    }

    public @NotNull List<Build> getBuilds(int foundationID, String type) {
        return getBuilds(false).stream().filter(build -> build.foundationID == foundationID && build.type.equals(type)).collect(Collectors.toList());
    }

    public @Nullable Build getBuild(PluginLocation location) {
        Optional<Build> optionalBuild = getBuilds(false).stream().filter(build -> build.getBlockLocations().contains(location)).findFirst();

        return optionalBuild.orElse(null);
    }

    public @Nullable Build getBuild(int id) {
        Optional<Build> optionalBuild = getBuilds(false).stream().filter(build -> build.id == id).findFirst();

        return optionalBuild.orElse(null);
    }
}
