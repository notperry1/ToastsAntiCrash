package net.cb2k.toastsanticrash.Events;

import net.cb2k.toastsanticrash.Utils.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlockCreativeEvents implements Listener {

    private ConfigManager configManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        configManager = ConfigManager.getInstance();
        if(e.getPlayer().isOp() || configManager.canCreative(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            e.getPlayer().kickPlayer("No creative for you nigger");

        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){
        configManager = ConfigManager.getInstance();
        if(e.getPlayer().isOp() || configManager.canCreative(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            e.getPlayer().kickPlayer("No creative for you nigger");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        configManager = ConfigManager.getInstance();
        if(e.getPlayer().isOp() || configManager.canCreative(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            e.getPlayer().kickPlayer("No creative for you nigger");
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e){
        configManager = ConfigManager.getInstance();
        if(e.getPlayer().isOp() || configManager.canCreative(e.getPlayer().getUniqueId())) return;
        if(e.getNewGameMode() != GameMode.SURVIVAL){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
            e.setCancelled(true);
            e.getPlayer().kickPlayer("No creative for you nigger");
        }
    }
}
