package net.cb2k.toastsanticrash.Utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CooldownManager {

    private final HashMap<Player, Integer> cooldowns = new HashMap<Player, Integer>();

    public void setCooldown(Player player, int time) {
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(Player player) {
        return cooldowns.getOrDefault(player, 0);
    }
}
