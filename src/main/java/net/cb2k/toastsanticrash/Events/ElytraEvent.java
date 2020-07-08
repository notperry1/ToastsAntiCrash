package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.Main;
import net.cb2k.toastsanticrash.Utils.ConfigManager;
import net.cb2k.toastsanticrash.Utils.CooldownManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ElytraEvent implements Listener {

    private final int TPS;
    public final CooldownManager cooldownManager = new CooldownManager();
    private ConfigManager configManager;
    private final Main plugin;


    public ElytraEvent(Main main) {
        this.plugin = main;
        TPS = (int) Main.tps;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGlide(EntityToggleGlideEvent event) {
        configManager = configManager.getInstance();
        if(configManager.getElytraDisableTps() == -1) return;
        if(TPS <= configManager.getElytraDisableTps()) {
            if (event.getEntity().getType().equals(EntityType.PLAYER))
                if(!configManager.unequipElytra()){
                    event.setCancelled(true);
                }else{
                    dequipElytra((Player) event.getEntity());
                }
        }
    }

    private void dequipElytra(Player player) {
        configManager = configManager.getInstance();
        if(configManager.getElytraDisableTps() == -1) return;
        if (TPS <= configManager.getElytraDisableTps()) {
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

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        configManager = configManager.getInstance();
        PlayerInventory inv = e.getPlayer().getInventory();
        int timeLeft = cooldownManager.getCooldown(e.getPlayer());

        if(e.getPlayer().hasPermission("speedlimiter.bypass") || e.getPlayer().hasPermission("speedlimit.bypass")) return;
        if(configManager.getElytraDisableTps() == -1) return;
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
            if(inv.getItemInMainHand().getType() == Material.FIREWORK || inv.getItemInOffHand().getType() == Material.FIREWORK) {
                if(inv.getArmorContents()[2].getType() == Material.ELYTRA) {
                    if(timeLeft == 0) {
                        cooldownManager.setCooldown(e.getPlayer(), configManager.getElytraCooldown());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                int timeLeft = cooldownManager.getCooldown(e.getPlayer());
                                cooldownManager.setCooldown(e.getPlayer(), --timeLeft);
                                if(timeLeft == 0) {
                                    cancel();
                                }
                            }
                        }.runTaskTimer(this.plugin, 20, 20);
                    } else {
                        String message = configManager.getCooldownMessage(cooldownManager.getCooldown(e.getPlayer()));
                        e.getPlayer().sendTitle("", message, 10, 70, 20);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
