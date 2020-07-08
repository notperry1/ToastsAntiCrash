package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.Utils.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class IllegalEvents implements Listener {

    private ConfigManager configManager;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        configManager = ConfigManager.getInstance();
        if(e.getPlayer().isOp()) return;
        if(configManager.isBlocked(e.getBlock().getType())){
            if(e.getBlock().getType() == Material.ENDER_PORTAL_FRAME) {
                if (e.getItemInHand().getType() == Material.EYE_OF_ENDER) return;
            }
            e.getItemInHand().setAmount(0);
            e.getPlayer().sendMessage(ChatColor.RED + "Don't waste your illegals dumb fuck");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e){
        configManager = ConfigManager.getInstance();
        if(configManager.isBlocked(e.getItem().getType())){
            e.getItem().setAmount(0);
            e.setCancelled(true);
        }
    }
}
