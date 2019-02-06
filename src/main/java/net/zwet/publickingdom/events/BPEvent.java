package net.zwet.publickingdom.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BPEvent implements Listener {
    PublicKingdom plugin;

    public BPEvent(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void BlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        Kingdom kingdom = new Kingdom(player);
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

        if (player instanceof Player) {
            if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).size() != 0) {
                for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(event.getBlockPlaced().getLocation())) {
                    String hr = (kingdomRegion.getId().charAt(0) + "").toUpperCase() + kingdomRegion.getId().substring(1);
                    Kingdom buildkingdom = null;
                    try {
                        buildkingdom = new Kingdom(hr);
                    } catch (NoSuchKingdomException | NullPointerException e) {
                        return;
                    }
                    if (buildkingdom != null) {
                        if (kingdomRegion != null) {
                            if (buildkingdom.getRegion().getId() != null) {
                                if (playerdata.getKingdomName() != null) {
                                    if (buildkingdom.getName() != null) {
                                        if ((kingdomRegion.getId().equalsIgnoreCase(buildkingdom.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) && !playerdata.getKingdomName().equals(buildkingdom.getName()) && !player.hasPermission("FireKingdom.Staff")) {
                                            if (buildkingdom.hasFlag("enemy-build")) {
                                                event.setCancelled(true);
                                                player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je mag niet in andermans border bouwen!");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void wlPlace(PlayerBucketEmptyEvent event){
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        Kingdom kingdom = new Kingdom(player);
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

        if (player instanceof Player) {
            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(event.getBlockClicked().getLocation())) {
                String hr = (kingdomRegion.getId().charAt(0) + "").toUpperCase() + kingdomRegion.getId().substring(1);
                Kingdom buildkingdomdata = null;
                try {
                    buildkingdomdata = new Kingdom(hr);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    Bukkit.getLogger().warning(hr + " bestaat niet! {BPEvent: 75}");
                }
                if (buildkingdomdata != null) {
                    if ((kingdomRegion.getId().equalsIgnoreCase(buildkingdomdata.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) && !playerdata.getKingdomName().equals(buildkingdomdata.getName())  && !player.hasPermission("FireKingdom.Staff")) {
                        if (buildkingdomdata.hasFlag("enemy-build")) {
                            event.setCancelled(true);
                            player.sendMessage(fireprefix + " " + ChatColor.GRAY+ "Je mag niet in andermans border bouwen!");
                        }
                    }
                }
            }

        }
    }
}
