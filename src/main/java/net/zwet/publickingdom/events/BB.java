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


        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
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
                if (buildkingdom.getRegion().getId() != null) {
                    if (playerdata.getKingdomName() != null) {
                        if (buildkingdom.getName() != null) {
                            if ((kingdomRegion.getId().equalsIgnoreCase(buildkingdom.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) && !playerdata.getKingdomName().equals(buildkingdom.getName()) && !player.hasPermission("publickingdom.staff")) {
                                if (buildkingdom.hasFlag("enemy-break")) {
                                    event.setCancelled(true);
                                    player.sendMessage(prefix + " " + ChatColor.GRAY + "Je mag geen blokken slopen in dit kingdom!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

