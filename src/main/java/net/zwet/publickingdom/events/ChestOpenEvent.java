package net.zwet.publickingdom.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ChestOpenEvent implements Listener {
    PublicKingdom plugin;

    public ChestOpenEvent(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void ChestOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        Kingdom kingdom = new Kingdom(player);
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

        if (event.getInventory().getHolder() instanceof Chest) {
            Chest chest = (Chest) event.getInventory().getHolder();
            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(chest.getLocation())) {
                String hr = (kingdomRegion.getId().charAt(0) + "").toUpperCase() + kingdomRegion.getId().substring(1);
                Kingdom buildkingdom = null;
                try {
                    buildkingdom = new Kingdom(hr);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    return;
                }
                if (buildkingdom != null) {
                    if (kingdomRegion.getId() != null && buildkingdom.getRegion().getId() != null && playerdata.getKingdomName() != null && buildkingdom.getName() != null) {
                        if (kingdomRegion.getId().equalsIgnoreCase(buildkingdom.getRegion().getId()) && !playerdata.getKingdomName().equals(buildkingdom.getName()) && !event.getPlayer().hasPermission("FireKingdom.Staff")) {
                            if (buildkingdom.hasFlag("enemy-chest-open")) {
                                event.setCancelled(true);
                                event.getPlayer().sendMessage(fireprefix + " " + ChatColor.GRAY + "Je mag niet in andermans border chesten openen!");
                            }
                        }
                    }
                }
            }
        } else if (event.getInventory().getHolder() instanceof DoubleChest) {
            DoubleChest dchest = (DoubleChest) event.getInventory().getHolder();
            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(dchest.getLocation())) {
                String hr = (kingdomRegion.getId().charAt(0) + "").toUpperCase() + kingdomRegion.getId().substring(1);
                Kingdom buildkingdom = null;
                try {
                    buildkingdom = new Kingdom(hr);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    return;
                }
                if (buildkingdom != null) {
                    if (kingdomRegion.getId() != null && buildkingdom.getRegion().getId() != null && playerdata.getKingdomName() != null && buildkingdom.getName() != null) {
                        if (kingdomRegion.getId().equalsIgnoreCase(buildkingdom.getRegion().getId()) && !playerdata.getKingdomName().equals(buildkingdom.getName()) && !event.getPlayer().hasPermission("FireKingdom.Staff")) {
                            if (buildkingdom.hasFlag("enemy-chest-open")) {
                                event.setCancelled(true);
                                event.getPlayer().sendMessage(fireprefix + " " + ChatColor.GRAY + "Je mag niet in andermans border chesten openen!");
                            }
                        }
                    }
                }
            }
        }
    }
}

