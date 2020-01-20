package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Define implements CommandExecutor {
    PublicKingdom plugin;

    public Define(PublicKingdom instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                if (player.hasPermission("publickingdom.staff")) {
                    Playerdata PlayerData = new Playerdata(player);
                    Kingdom kingdom = null;
                    try {
                        kingdom = new Kingdom(args[0]);
                    } catch (NoSuchKingdomException | NullPointerException e) {
                        Bukkit.getLogger().warning(args[0] + " bestaat niet! Define{40}");
                    }
                    if (kingdom != null) {
                        kingdom.setRegion(player);
                    }
                } else {
                    player.sendMessage(prefix + " " + ChatColor.RED + "Je staat niet in een worldguard region!");
                }
            }else if (args.length == 2){
                Playerdata playerdata = new Playerdata(player);
                Kingdom kingdom = null;
                try {
                    kingdom = new Kingdom(args[0]);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    Bukkit.getLogger().warning(args[0] + " bestaat niet! Define{54}");
                }
                if (player.hasPermission("publickingdom.staff")){
                    if (kingdom != null){
                        kingdom.setRegion(args[1]);
                    }
                }
            }
        }


        return true;
    }
}
