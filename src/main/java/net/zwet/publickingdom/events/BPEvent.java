package net.zwet.publickingdom.events;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
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
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

        if (player instanceof Player) {
            LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);
            RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = regionContainer.createQuery();
            if (query.getApplicableRegions(lplayer.getLocation()).size() != 0) {
                ApplicableRegionSet set = query.getApplicableRegions(lplayer.getLocation());
                for (ProtectedRegion kingdomRegion : set) {
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
                                        if ((kingdomRegion.getId().equalsIgnoreCase(buildkingdom.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) && !playerdata.getKingdomName().equals(buildkingdom.getName()) && !player.hasPermission("publickingdom.Staff")) {
                                            if (buildkingdom.hasFlag("enemy-build")) {
                                                event.setCancelled(true);
                                                player.sendMessage(prefix + " " + ChatColor.GRAY + "Je mag niet in andermans border bouwen!");
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
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

        if (player instanceof Player) {
            LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);
            RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = regionContainer.createQuery();
            if (query.getApplicableRegions(lplayer.getLocation()).size() != 0) {
                ApplicableRegionSet set = query.getApplicableRegions(lplayer.getLocation());
                for (ProtectedRegion kingdomRegion : set) {
                    String hr = (kingdomRegion.getId().charAt(0) + "").toUpperCase() + kingdomRegion.getId().substring(1);
                    Kingdom buildkingdomdata = null;
                    try {
                        buildkingdomdata = new Kingdom(hr);
                    } catch (NoSuchKingdomException | NullPointerException e) {
                        Bukkit.getLogger().warning(hr + " bestaat niet! {BPEvent: 75}");
                    }
                    if (buildkingdomdata != null) {
                        if ((kingdomRegion.getId().equalsIgnoreCase(buildkingdomdata.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) && !playerdata.getKingdomName().equals(buildkingdomdata.getName()) && !player.hasPermission("publickingdom.Staff")) {
                            if (buildkingdomdata.hasFlag("enemy-build")) {
                                event.setCancelled(true);
                                player.sendMessage(prefix + " " + ChatColor.GRAY + "Je mag niet in andermans border bouwen!");
                            }
                        }
                    }
                }
            }
        }
    }
}
