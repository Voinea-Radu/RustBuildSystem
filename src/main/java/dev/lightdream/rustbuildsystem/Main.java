package dev.lightdream.rustbuildsystem;

import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.files.config.SQLConfig;
import dev.lightdream.api.utils.LangUtils;
import dev.lightdream.rustbuildsystem.commands.BuildCommand;
import dev.lightdream.rustbuildsystem.commands.UpgradeCommand;
import dev.lightdream.rustbuildsystem.files.config.Config;
import dev.lightdream.rustbuildsystem.files.config.Lang;
import dev.lightdream.rustbuildsystem.managers.DatabaseManager;
import dev.lightdream.rustbuildsystem.managers.EventManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public final class Main extends LightDreamPlugin {

    public static Main instance;

    //Managers
    public EventManager eventManager;
    public DatabaseManager databaseManager;

    //Configs
    public Config config;
    public Lang lang;

    @Override
    public void onEnable() {
        init("RustBuildSystem", "rbs", "1.3");
        instance = this;
        eventManager = new EventManager(this);
        databaseManager = new DatabaseManager(this);
    }

    @Override
    public void onDisable() {
        eventManager.buildMode.forEach((user, build) -> {
            build.placeholders.forEach(location -> {
                location.setBlock(Material.AIR);
            });
        });

        databaseManager.save();
    }

    @Override
    public @NotNull String parsePapi(OfflinePlayer offlinePlayer, String s) {
        return "";
    }

    @Override
    public void loadConfigs() {
        sqlConfig = fileManager.load(SQLConfig.class);
        config = fileManager.load(Config.class);
        baseConfig = config;
        lang = (Lang) fileManager.load(LangUtils.getLang(Main.class, config.lang));
        baseLang = lang;
    }

    @Override
    public void loadBaseCommands() {
        baseCommands.add(new BuildCommand(this));
        baseCommands.add(new UpgradeCommand(this));
    }


}
