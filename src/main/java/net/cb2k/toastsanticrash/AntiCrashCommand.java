package net.cb2k.toastsanticrash;

import net.cb2k.toastsanticrash.Utils.ConfigManager;
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
        ConfigManager configManager = ConfigManager.getInstance();
        if(sender instanceof Player){
            if(!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "No.");
                return true;
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("debug")){
                        boolean bool = !(TPS <= configManager.getElytraDisableTps());
                        boolean bool2 = TPS <= configManager.getRedstoneDisableTps();
                        boolean bool3 = ConfigManager.getInstance() == null;
                        String string;
                        if(configManager.disableSpawns()){
                            string = ChatColor.GOLD + "Mob Spawn Chance: " + ChatColor.GREEN + "0" + " \n ";
                        } else {
                            string = ChatColor.GOLD + "Mob Spawn Chance: " + ChatColor.GREEN + configManager.getChanceAt(TPS) + " \n ";
                        }
                    String sb = ChatColor.GOLD + "ConfigManager is null? " + ChatColor.GREEN + bool3 + "\n" +
                            ChatColor.GOLD + "\nTPS: " + ChatColor.GREEN + TPS + "\n" +
                            ChatColor.GOLD + "Disable Elytra at: " + ChatColor.GREEN + configManager.getElytraDisableTps() + "\n" +
                            ChatColor.GOLD + "Can Glide: " + ChatColor.GREEN + bool + "\n" +
                            ChatColor.GOLD + "Disable Redstone at: " + ChatColor.GREEN + configManager.getRedstoneDisableTps() + "\n" +
                            ChatColor.GOLD + "Redstone Disabled: " + ChatColor.GREEN + bool2 + "\n" + string +
                            ChatColor.GOLD + "\nIgnored Spawns: " + "\n" + ChatColor.GREEN + configManager.ignoredSpawns() + "\n" +
                            ChatColor.GOLD + "Allowed Creative: " + "\n" + ChatColor.GREEN + configManager.allowedCreative() + "\n" +
                            ChatColor.GOLD + "Blocked Blocks: " + "\n" + ChatColor.GREEN + configManager.blockedBlocks();
                    sender.sendMessage(sb + "\n");
                        return true;
                }
                if(args[0].equalsIgnoreCase("reload")){
                    configManager.reload();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
                    return true;
                }
            }
            else { sender.sendMessage(ChatColor.RED + "Computer says no.");}
        }
        return true;
    }
}
