package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.ConfigManager;
import net.cb2k.toastsanticrash.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ElytraEvent implements Listener {

    private final int TPS;

    public ElytraEvent(Main main) {
        TPS = (int) Main.tps;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGlide(EntityToggleGlideEvent event) {
        if(ConfigManager.getElytraDisableTps() == -1) return;
        if(TPS < ConfigManager.getElytraDisableTps()) {
            if (event.getEntity().getType().equals(EntityType.PLAYER))
                if(!ConfigManager.unequipElytra()){
                    event.setCancelled(true);
                }else{
                    dequipElytra((Player) event.getEntity());
                }
        }
    }

    private void dequipElytra(Player player) {
        if(ConfigManager.getElytraDisableTps() == -1) return;
        if (TPS < ConfigManager.getElytraDisableTps()) {
            PlayerInventory i = player.getInventory();
            if (!((i.getChestplate() != null) && i.getChestplate().getType().equals(Material.ELYTRA)))
                return;

            ItemStack elytra = i.getChestplate();
            i.setChestplate(null);

            // inventory full?
            if (i.firstEmpty() != -1) {
                i.addItem(elytra);
            } else {
                Location l = i.getLocation();
                l.getWorld().dropItemNaturally(l, elytra);
                player.updateInventory();
            }
        }
    }
}
