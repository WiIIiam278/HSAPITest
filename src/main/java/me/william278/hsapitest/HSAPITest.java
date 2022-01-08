package me.william278.hsapitest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HSAPITest extends JavaPlugin {

    private static HSAPITest instance;
    public static HSAPITest getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCmd());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
