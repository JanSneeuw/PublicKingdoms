package net.zwet.publickingdom.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Sethome implements CommandExecutor {
    PublicKingdom plugin;

    public Sethome(PublicKingdom instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            if (args.length == 1) {
                if (player.hasPermission("firekingdom.home")) {
                    Playerdata playerdata = null;
                    try {
                        playerdata = new Playerdata(player);
                    } catch (NullPointerException e) {
                        Bukkit.getLogger().warning(player.getName() + " probeerde zijn home te setten maar hij heeft geen playerdata {Sethome: 19}");
                    }
                    if (playerdata != null) {
                        if (playerdata.isInKingdom()) {
                            Kingdom kingdom = new Kingdom(player);
                            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
                                if (kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId())) {
                                    if (!playerdata.hasHome(args[0])) {
                                        if (player.hasPermission("firekingdom.home." + (playerdata.getHomeAmount() + 1)) || playerdata.getHomeAmount() == 0) {
                                            try {
                                                playerdata.setHome(player, args[0]);
                                            } catch (IOException e) {
                                                Bukkit.getLogger().warning("Kan " + player.getName() + "'s playerdata niet opslaan na zetten van home {Sethome: 35}");
                                            }
                                            player.sendMessage(fireprefix + ChatColor.GRAY + " Je hebt je home naar je huidige locatie!");
                                        }
                                    }else{
                                        player.sendMessage(fireprefix + ChatColor.GRAY + "Dit home bestaat al, verwijder hem eerst om deze naam nogmaals te gebruiken!");
                                    }
                                }else{
                                    player.sendMessage(fireprefix + ChatColor.GRAY + " Je staat niet in je eigen border!");
                                }
                            }
                        }
                    }
                }else{
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt geen toegang tot dit command!");
                }
            }else{
                player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt geen naam gegeven, gebruik /k sethome <naam>");
            }
        }
        return true;
    }
}
