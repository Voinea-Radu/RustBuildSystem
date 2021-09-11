package dev.lightdream.rustbuildsystem;

import dev.lightdream.api.API;
import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.config.SQLConfig;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustbuildsystem.commands.BuildCommand;
import dev.lightdream.rustbuildsystem.commands.UpgradeCommand;
import dev.lightdream.rustbuildsystem.files.config.Config;
import dev.lightdream.rustbuildsystem.files.config.Lang;
import dev.lightdream.rustbuildsystem.managers.DatabaseManager;
import dev.lightdream.rustbuildsystem.managers.EventManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

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
        init("RustBuildSystem", "rbs", "1.10");
        instance = this;
        eventManager = new EventManager(this);
        databaseManager = new DatabaseManager(this);
    }

    @Override
    public void onDisable() {
        eventManager.buildMode.forEach((user, build) -> build.placeholders.forEach(location -> location.setBlock(Material.AIR)));

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
        lang = fileManager.load(Lang.class, fileManager.getFile(baseConfig.baseLang));
        baseLang = lang;
    }

    @Override
    public void loadBaseCommands() {
        baseCommands.add(new BuildCommand(this));
        baseCommands.add(new UpgradeCommand(this));
    }

    @Override
    public MessageManager instantiateMessageManager() {
        return new MessageManager(this, Main.class);
    }

    @Override
    public void registerLangManager() {
        API.instance.langManager.register(Main.class, getLangs());
    }

    @Override
    public void setLang(Player player, String s) {
        User user = databaseManager.getUser(player);
        user.setLang(s);
        databaseManager.save(user);
    }

    @Override
    public HashMap<String, Object> getLangs() {
        HashMap<String, Object> langs = new HashMap<>();

        baseConfig.langs.forEach(lang -> {
            Lang l = fileManager.load(Lang.class, fileManager.getFile(lang));
            langs.put(lang, l);
        });

        return langs;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }


}
