package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.Utils.ConfigManager;
import net.cb2k.toastsanticrash.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

public class MobSpawnEvent implements Listener {

    private  ConfigManager configManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void SpawnEvent(EntitySpawnEvent e) {
        configManager = configManager.getInstance();
        if(configManager.disableSpawns()){
//            if(configManager.isIgnored(e.getEntity())) return;
            e.setCancelled(true);
        }
//        if(configManager.isIgnored(e.getSpawnReason())) return;
        int tps = (int) Main.tps;
        int randNum = ThreadLocalRandom.current().nextInt(100) + 1;
        int chance = configManager.getChanceAt(tps);
        if(randNum > chance || configManager.disableSpawns()) {
            e.setCancelled(true);
            e.getEntity();
        }
    }
}