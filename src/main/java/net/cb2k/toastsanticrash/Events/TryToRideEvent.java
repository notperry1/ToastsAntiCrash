package net.cb2k.toastsanticrash.Events;

import org.bukkit.entity.Donkey;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class TryToRideEvent implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() != null) {
            if (!(e.getRightClicked() instanceof Vehicle)) return;
            if ((e.getRightClicked() instanceof Donkey)) return;
            if ((e.getRightClicked() instanceof Llama)) return;
            if ((e.getRightClicked() instanceof Horse)) return;
            e.getRightClicked().addPassenger(e.getPlayer());
        }
    }
}
