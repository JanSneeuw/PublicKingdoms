package net.zwet.publickingdom.commands;


import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
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
            String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            if (args.length == 1) {
                if (player.hasPermission("publickingdom.home")) {
                    Playerdata playerdata = null;
                    try {
                        playerdata = new Playerdata(player);
                    } catch (NullPointerException e) {
                        Bukkit.getLogger().warning(player.getName() + " probeerde zijn home te setten maar hij heeft geen playerdata {Sethome: 19}");
                    }
                    if (playerdata != null) {
                        if (playerdata.isInKingdom()) {
                            Kingdom kingdom = new Kingdom(player);
                            LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);
                            RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                            RegionQuery query = regionContainer.createQuery();
                            if (query.getApplicableRegions(lplayer.getLocation()).size() > 0) {
                                ApplicableRegionSet set = query.getApplicableRegions(lplayer.getLocation());
                                for (ProtectedRegion kingdomRegion : set) {
                                    if (kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId())) {
                                        if (!playerdata.hasHome(args[0])) {
                                            if (player.hasPermission("publickingdom.home." + (playerdata.getHomeAmount() + 1)) || playerdata.getHomeAmount() == 0) {
                                                try {
                                                    playerdata.setHome(player, args[0]);
                                                } catch (IOException e) {
                                                    Bukkit.getLogger().warning("Kan " + player.getName() + "'s playerdata niet opslaan na zetten van home {Sethome: 35}");
                                                }
                                                player.sendMessage(prefix + ChatColor.GRAY + " Je hebt je home naar je huidige locatie!");
                                            }
                                        } else {
                                            player.sendMessage(prefix + ChatColor.GRAY + "Dit home bestaat al, verwijder hem eerst om deze naam nogmaals te gebruiken!");
                                        }
                                    } else {
                                        player.sendMessage(prefix + ChatColor.GRAY + " Je staat niet in je eigen border!");
                                    }
                                }
                            }
                        }
                    }
                }else{
                    player.sendMessage(prefix + " " + ChatColor.GRAY + "Je hebt geen toegang tot dit command!");
                }
            }else{
                player.sendMessage(prefix + " " + ChatColor.GRAY + "Je hebt geen naam gegeven, gebruik /k sethome <naam>");
            }
        }
        return true;
    }
}
