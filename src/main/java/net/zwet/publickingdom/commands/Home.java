package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Home implements CommandExecutor {
    PublicKingdom plugin;

    public Home(PublicKingdom instance) { plugin = instance; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        if (sender instanceof Player && args.length == 1){
            Player player = (Player) sender;
                Validator homeValidator = new Validator().addValidation(new HasBukkitPermissionValidation(player, "publickingdom.home"))
                        .addValidation(new InKingdomValidation(player)).addValidation(new NotSpawningValidation(player)).addValidation(new HasCertainHome(player, args[0]));
                Playerdata playerdata = null;
                try {
                    playerdata = new Playerdata(player);
                } catch (NullPointerException e) {
                    Bukkit.getLogger().warning(player.getName() + " probeerde naar zijn home te gaan maar heeft geen playerdata {Home: 26}");
                }
                boolean passOn = homeValidator.executeValidations();
                if (passOn) {
                    player.sendMessage(prefix + ChatColor.GRAY + " Je wordt over 5 seconden naar je home geteleport!");
                    Playerdata finalPlayerdata = playerdata;
                    BukkitTask runnable = new BukkitRunnable() {
                            public void run() {
                                if (Spawn.hspawner.containsKey(player)) {
                                    player.teleport(finalPlayerdata.getHome(args[0]));
                                    Spawn.hspawner.remove(player);
                                    player.sendMessage(prefix + ChatColor.GRAY + " Je bent nu bij je home!");
                                }
                            }
                        }.runTaskLater(plugin, 100L);
                        Spawn.hspawner.put(player, runnable);
                } else {
                    player.sendMessage(prefix + " " + ChatColor.GRAY + homeValidator.getErrormessage());
                }
        }
        return true;
    }
}

