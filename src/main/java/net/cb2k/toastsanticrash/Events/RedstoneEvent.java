package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.ConfigManager;
import net.cb2k.toastsanticrash.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RedstoneEvent implements Listener {

    private final int TPS;

    public RedstoneEvent(Main main) {
        TPS = (int) Main.tps;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRedstone(BlockRedstoneEvent event){
        if(TPS < ConfigManager.getRedstoneDisableTps()) {
            event.setNewCurrent(0);
        }
    }
}
