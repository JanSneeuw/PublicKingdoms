package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Spawn implements CommandExecutor {
    PublicKingdom plugin;

    public Spawn(PublicKingdom instance) {
        plugin = instance;
    }
    public static Map<Player, BukkitTask> hspawner = new HashMap<>();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            Playerdata playerdata = new Playerdata(player);
            String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            if (playerdata.isInKingdom()) {
                if (!hspawner.containsKey(player)) {
                    Kingdom kingdom = new Kingdom(player);
                    player.sendMessage(prefix + " " + ChatColor.GRAY + "Je wordt over§f 5 seconden §7naar je kingdom spawn geteleport!");
                    BukkitTask runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (hspawner.containsKey(player)) {
                                player.teleport(kingdom.getSpawn());
                                hspawner.remove(player);
                                player.sendMessage(prefix + " " + ChatColor.GRAY + "Je bent nu bij je kingdom spawn!");
                            }
                        }
                    }.runTaskLater(plugin, 100L);
                    hspawner.put(player, runnable);
                }else{
                    player.sendMessage(prefix + " " + ChatColor.GRAY + "Je wordt al geteleport naar je kingdom spawn!");
                }
            }
        }
        return true;
    }
}

