package net.zwet.publickingdom.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetHertogdomSpawn implements CommandExecutor {
    PublicKingdom plugin;

    public SetHertogdomSpawn(PublicKingdom instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            Player player = (Player)sender;
            Playerdata playerdata = new Playerdata(player);
            String fireprefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().get("Message-Prefix").toString());
            int X = player.getLocation().getBlockX();
            int Y = player.getLocation().getBlockY();
            int Z = player.getLocation().getBlockZ();
            World world = Bukkit.getWorld(this.plugin.getConfig().getString("Kingdom-World"));
            if (playerdata.isInKingdom()) {
                Kingdom kingdom = new Kingdom(player);
                if (playerdata.hasPermission("k.sethertogdomlocation")) {
                    if (kingdom.hasHertogdom()) {
                        if (kingdom.hertogdomExists(args[0])) {
                            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(world).getApplicableRegions(player.getLocation())) {
                                if (kingdomRegion.getId().equalsIgnoreCase(kingdom.getHertogdomRegion(args[0]).getId())) {
                                    try {
                                        kingdom.setHertogdomLocation(args[0], player);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    player.sendMessage(fireprefix + " " + ChatColor.BLUE + "Je hebt je hertogdom spawn verplaatst naar je huidige locatie!");
                                    return true;
                                } else {
                                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Deze locatie is niet in je hertogdom border!");
                                    return true;
                                }
                            }
                        }
                    }
                }
                else
                {
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt niet de juiste permissions om dit te doen!");
                    return true;
                }
            }
        }
        return true;
    }
}


