package net.cb2k.toastsanticrash.Utils;

import net.cb2k.toastsanticrash.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfigManager {
    private ConfigManager configManager;
    private final int TPS;
    private static Plugin plugin;
    private FileConfiguration config;

    public ConfigManager(Plugin plugin) {
        ConfigManager.plugin = plugin;
        config = plugin.getConfig();
        TPS = (int) Main.tps;

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

    public void save(){
        config.getInt("elytra.disable-tps");
        plugin.saveConfig();
    }

    public void reload(){
        plugin.reloadConfig();
        plugin.saveConfig();
    }

    public int getChanceAt(Integer tps) {
        return config.getInt("mobs.spawn-chance." + tps.toString());

    }

    public int getElytraDisableTps() {
        return config.getInt("elytra.disable-tps");
    }
    public int getElytraCooldown() {return config.getInt("elytra.rocket-cooldown"); }

    public String getCooldownMessage(Integer time) {
        return ChatColor.translateAlternateColorCodes('&', config.getString("elytra.cooldown-message").replace("%time%", time.toString()));
    }


    public boolean unequipElytra() { return config.getBoolean("elytra.unequip"); }


    public int getRedstoneDisableTps() {
        return config.getInt("redstone.disable-tps");
    }

    private String getConfigVersion() {
        return config.getString("config-version");
    }

    public ConfigManager getInstance() {
        if(configManager == null) {
            configManager = new ConfigManager(plugin);
        }
        return configManager;
    }

    public boolean disableSpawns(){
        return config.getBoolean("mobs.spawn-chance.none");
    }

    public boolean isIgnored(CreatureSpawnEvent.SpawnReason spawnReason){
        final Set<CreatureSpawnEvent.SpawnReason> ignore = plugin.getConfig().getStringList("mobs.spawn-chance.ignore").stream()
                .map(CreatureSpawnEvent.SpawnReason::valueOf)
                .collect(Collectors.toSet());
        return ignore.contains(spawnReason);
    }

    public String ignoredSpawns(){
        return Main.instance.getConfig().getStringList("mobs.spawn-chance.ignore").toString();
    }

    public boolean isBlocked(Material material){
        final Set<Material> blocked = plugin.getConfig().getStringList("blocked-blocks").stream()
                .map(Material::valueOf)
                .collect(Collectors.toSet());
        return blocked.contains(material);
    }

    public String blockedBlocks(){
        return Main.instance.getConfig().getStringList("blocked-blocks").toString();
    }

    public boolean canCreative(UUID uuid){
        if(plugin.getConfig().getStringList("allow-creative").contains(uuid.toString())){
            plugin.getConfig().getStringList("allow-cretive").add(Bukkit.getPlayer(uuid).getDisplayName());
            plugin.getConfig().getStringList("allow-creative").remove(uuid.toString());
            return true;
        }
        return plugin.getConfig().getStringList("allow-creative").contains(Bukkit.getPlayer(uuid).getDisplayName());
    }

    public void allowCreative(UUID uuid){
        plugin.getConfig().getStringList("allow-creative").add(Bukkit.getPlayer(uuid).getDisplayName());
    }

    public String allowedCreative(){
        return Main.instance.getConfig().getStringList("allow-creative").toString();
    }
}
