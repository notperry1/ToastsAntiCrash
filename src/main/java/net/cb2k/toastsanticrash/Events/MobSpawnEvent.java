package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.Utils.ConfigManager;
import net.cb2k.toastsanticrash.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

public class MobSpawnEvent implements Listener {



    @EventHandler(priority = EventPriority.HIGHEST)
    public void SpawnEvent(CreatureSpawnEvent e) {
        if(ConfigManager.isIgnored(e.getSpawnReason())) return;
        int tps = (int) Main.tps;
        int randNum = ThreadLocalRandom.current().nextInt(100) + 1;
        int chance = ConfigManager.getChanceAt(tps);
        if(randNum > chance || ConfigManager.disableSpawns()) {
            e.setCancelled(true);
            e.getEntity().setHealth(0);
        }
    }
}