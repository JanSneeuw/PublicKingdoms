package net.zwet.publickingdom.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import java.io.IOException;

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

public class Setspawn
        implements CommandExecutor
{
    PublicKingdom plugin;

    public Setspawn(PublicKingdom instance)
    {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player)sender;
        Playerdata playerdata = new Playerdata(player);
        Kingdom kingdom = new Kingdom(player);
        String fireprefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().get("Message-Prefix").toString());
        int X = player.getLocation().getBlockX();
        int Y = player.getLocation().getBlockY();
        int Z = player.getLocation().getBlockZ();
        World world = Bukkit.getWorld(this.plugin.getConfig().getString("Kingdom-World"));
        if ((args.length == 0) && (playerdata.isInKingdom())) {
            if (playerdata.hasPermission("k.setspawn")){
                for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(world).getApplicableRegions(player.getLocation())){
                    if (kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId())) {
                        try {
                            kingdom.setSpawn(player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage(fireprefix + " " + ChatColor.BLUE + "Je hebt je kingdom spawn verplaatst naar je huidige locatie!");
                        return true;
                        }else{
                            player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Deze locatie is niet in je border!");
                            return true;
                    }
                }
            }
            else{
                player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt niet de juiste permissions om dit te doen!");
                return true;
            }
        }
        return true;
    }
}
