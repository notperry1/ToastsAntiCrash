package net.cb2k.toastsanticrash;

import net.cb2k.toastsanticrash.Events.ElytraEvent;
import net.cb2k.toastsanticrash.Events.MobSpawnLimiter;
import net.cb2k.toastsanticrash.Events.RedstoneEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static float tps = 20.0f;
    public static boolean debugMode;


    @Override
    public void onEnable() {
        instance = this;
        new ConfigManager(this);

        getServer().getPluginManager().registerEvents(new ElytraEvent(this), this);
        getServer().getPluginManager().registerEvents(new RedstoneEvent(this), this);
        getServer().getPluginManager().registerEvents(new MobSpawnLimiter(), this);
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
            }
        }, 0, 1);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
