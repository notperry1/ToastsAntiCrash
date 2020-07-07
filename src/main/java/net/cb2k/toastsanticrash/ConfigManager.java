package net.cb2k.toastsanticrash;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigManager {
    private static ConfigManager configManager;

    private static Plugin plugin;
    private static FileConfiguration config;

    public ConfigManager(Plugin plugin) {
        ConfigManager.plugin = plugin;
        config = plugin.getConfig();

        if(!(this.getConfigExists())) {
            plugin.saveDefaultConfig();
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!plugin.getConfig().contains("config-version") || plugin.getConfig().getInt("config-version") != 2) {
            configFile.delete();
            plugin.saveDefaultConfig();
        }
    }

    private boolean getConfigExists() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        return configFile.exists();
    }

    public static void reload(){
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public static int getChanceAt(Integer tps) {
        return config.getInt("mobs.spawn-chance." + tps.toString());
    }

    public static int getElytraDisableTps() {
        return config.getInt("elytra.disable-tps");
    }
    public static boolean unequipElytra() { return config.getBoolean("elytra.unequip"); }
    public static int getRedstoneDisableTps() {
        return config.getInt("redstone.disable-tps");
    }

    private String getConfigVersion() {
        return config.getString("config-version");
    }

    public static ConfigManager getInstance() {
        if(configManager == null) {
            configManager = new ConfigManager(plugin);
        }
        return configManager;
    }

    public static Boolean disableSpawns(){
        return config.getBoolean("mobs.spawn-chance.none");
    }

    public static boolean isIgnored(CreatureSpawnEvent.SpawnReason spawnReason){
        final Set<CreatureSpawnEvent.SpawnReason> ignore = plugin.getConfig().getStringList("mobs.spawn-chance.ignore").stream()
                .map(CreatureSpawnEvent.SpawnReason::valueOf)
                .collect(Collectors.toSet());
        return ignore.contains(spawnReason);
    }
}
