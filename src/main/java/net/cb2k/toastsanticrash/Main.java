package net.cb2k.toastsanticrash;

import net.cb2k.toastsanticrash.Events.*;
import net.cb2k.toastsanticrash.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static float tps = 20.0f;
    public ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        new ConfigManager(this);
        configManager = ConfigManager.getInstance();

        getServer().getPluginManager().registerEvents(new ElytraEvent(this), this);
        getServer().getPluginManager().registerEvents(new RedstoneEvent(this), this);
        getServer().getPluginManager().registerEvents(new MobSpawnEvent(), this);
        getServer().getPluginManager().registerEvents(new TryToRideEvent(), this);
        getServer().getPluginManager().registerEvents(new BlockCreativeEvents(), this);
        getServer().getPluginManager().registerEvents(new IllegalEvents(), this);

        getCommand("allowcreative").setExecutor(new AllowCreativeCommand());
        getCommand("anticrash").setExecutor(new AntiCrashCommand(this));

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            long start = 0;
            long now = 0;

            @Override
            public void run() {
                start = now;
                now = System.currentTimeMillis();
                long tdiff = now - start;
                if(tdiff != 0) {
                    tps = (float) (1000 / tdiff);
                }
                if(tps > 20.0f) {
                    tps = 20.0f;
                }
                for (Player player : Bukkit.getOnlinePlayers()){
                    if(player.getGameMode() != GameMode.SURVIVAL){
                        if(player.isOp()) { configManager.allowCreative(player.getUniqueId()); return; }
                        if(configManager.canCreative(player.getUniqueId())) return;
                        player.setGameMode(GameMode.SURVIVAL);
                        player.kickPlayer("No creative for you nigger");
                    }
                }
            }
        }, 0, 1);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
