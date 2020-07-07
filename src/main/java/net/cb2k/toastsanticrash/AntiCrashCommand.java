package net.cb2k.toastsanticrash;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AntiCrashCommand implements CommandExecutor {

    private final int TPS;

    public AntiCrashCommand(Main main) {
        TPS = (int) Main.tps;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(!sender.isOp()) return false;
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("debug")){
                        StringBuilder sb = new StringBuilder();
                        sb.append(ChatColor.GOLD + "TPS: " + TPS + "\n");
                        sb.append(ChatColor.GOLD + "Disable Elytra at: " + ConfigManager.getElytraDisableTps() + "\n");
                        sb.append(ChatColor.GOLD + "Disable Redstone at: " + ConfigManager.getRedstoneDisableTps() + "\n");
                        sb.append(ChatColor.GOLD + "Mob Spawn Chance: " + ConfigManager.getChanceAt(TPS) + "\n");
                        sender.sendMessage(String.valueOf(sb));
                        return true;
                }
                if(args[0].equalsIgnoreCase("reload")){
                    ConfigManager.reload();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
                    return true;
                }
            }
            else { sender.sendMessage(ChatColor.RED + "Computer says no.");}
        }
        return true;
    }
}
