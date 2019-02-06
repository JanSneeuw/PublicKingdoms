package net.zwet.publickingdom.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BB implements Listener {
    PublicKingdom plugin;

    public BB(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        Kingdom kingdom = null;
        try {
            kingdom = new Kingdom(player);
        }catch(NullPointerException e){
            return;
        }
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).size() != 0) {
            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(event.getBlock().getLocation())) {
                String hr = (kingdomRegion.getId().charAt(0) + "").toUpperCase() + kingdomRegion.getId().substring(1);
                Kingdom buildkingdom = null;
                try {
                    buildkingdom = new Kingdom(hr);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    return;
                }
                if (buildkingdom.getRegion().getId() != null) {
                    if (playerdata.getKingdomName() != null) {
                        if (buildkingdom.getName() != null) {
                            if ((kingdomRegion.getId().equalsIgnoreCase(buildkingdom.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) && !playerdata.getKingdomName().equals(buildkingdom.getName()) && !player.hasPermission("FireKingdom.staff")) {
                                if (buildkingdom.hasFlag("enemy-break")) {
                                    event.setCancelled(true);
                                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je mag geen blokken slopen in dit kingdom!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

