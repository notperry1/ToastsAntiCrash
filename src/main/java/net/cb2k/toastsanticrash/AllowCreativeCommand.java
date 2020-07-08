package net.cb2k.toastsanticrash;

import net.cb2k.toastsanticrash.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class AllowCreativeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigManager configManager = ConfigManager.getInstance();
        if(sender instanceof Player) {
            UUID uuid = ((Player) sender).getPlayer().getUniqueId();
            if (sender.isOp()) {
                try {
                    if(Bukkit.getPlayer(args[0]) != null){
                        sender.sendMessage("§aAdded: " + args[0]);
                        configManager.allowCreative(uuid);
                        configManager.save();
                        configManager.reload();
                        return true;
                    }
                } catch (Exception var6) {
                    sender.sendMessage("§cOH HELL NAH YALL FORGOT SOMETHIN");
                    return true;
                }
            } else {
                sender.sendMessage("§cOH HELL NAH BITCH U CANT DO THAT BOI");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "No.");
        return true;
    }
}
