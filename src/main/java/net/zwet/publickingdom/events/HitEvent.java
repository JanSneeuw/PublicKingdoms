package net.zwet.publickingdom.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitEvent implements Listener {
    PublicKingdom plugin;

    public HitEvent(PublicKingdom instance) {
        plugin = instance;
    }

    private ProtectedRegion region;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            Playerdata damagerdata = new Playerdata(damager);
            Playerdata damageddata = new Playerdata(damaged);
            Kingdom kingdom = new Kingdom(damaged);
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            if (damageddata.isInKingdom()) {
                cancel(event, damager, damaged, damagerdata, damageddata, kingdom, fireprefix);

            }
        } else if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player damager = (Player) arrow.getShooter();
                Player damaged = (Player) event.getEntity();

                Playerdata damagerdata = new Playerdata(damager);
                Playerdata damageddata = new Playerdata(damaged);
                Kingdom kingdom = new Kingdom(damaged);
                String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                if (damageddata.isInKingdom()) {
                    cancel(event, damager, damaged, damagerdata, damageddata, kingdom, fireprefix);
                }
            } else if (event.getDamager() instanceof FishHook && event.getEntity() instanceof Player) {
                FishHook fishHook = (FishHook) event.getDamager();
                if (fishHook.getShooter() instanceof Player) {
                    Player damager = (Player) fishHook.getShooter();
                    Player damaged = (Player) event.getEntity();

                    Playerdata damagerdata = new Playerdata(damager);
                    Playerdata damageddata = new Playerdata(damaged);
                    Kingdom kingdom = new Kingdom(damaged);
                    String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                    Validator hitValidator = new Validator().addValidation(new InKingdomValidation(damaged)).addValidation(new InRegionValidation(damaged, damaged.getWorld()))
                            .addValidation(new HasRegionValidation(kingdom).region(region)).addValidation(new KingdomHasFlagValidation(kingdom, "enemy-hit"))
                                .addValidation(new KingdomHasRegionValidation(kingdom)).addValidation(new KingdomHasFlagValidation(kingdom, "enemy-hit"));
                    if (damageddata.isInKingdom()) {
                        if (WGBukkit.getRegionManager(damaged.getWorld()).getApplicableRegions(damaged.getLocation()).size() != 0) {
                            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(damager.getWorld()).getApplicableRegions(damaged.getLocation())) {
                                this.region = kingdomRegion;
                                if (kingdomRegion.getId() != null && kingdom.getRegion().getId() != null) {
                                    if (kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) {
                                        if (kingdom.hasFlag("enemy-hit")) {
                                            if (damageddata.getKingdomName() != null && damagerdata.getKingdomName() != null) {
                                                if (!damageddata.getKingdomName().equals(damagerdata.getKingdomName()) && !damager.hasPermission("FireKingdom.Staff")) {
                                                    event.setCancelled(true);
                                                    damager.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je kan niet iemand slaan in zijn eigen border!");
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
    }

    private void cancel(EntityDamageByEntityEvent event, Player damager, Player damaged, Playerdata damagerdata, Playerdata damageddata, Kingdom kingdom, String fireprefix) {
        if (WGBukkit.getRegionManager(damaged.getWorld()).getApplicableRegions(damaged.getLocation()).size() != 0) {
            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(damager.getWorld()).getApplicableRegions(damaged.getLocation())) {
                assert kingdomRegion != null;
                if (kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId()) || kingdom.hasRegion(kingdomRegion)) {
                    if (kingdom.hasFlag("enemy-hit")) {
                        if (!damageddata.getKingdomName().equals(damagerdata.getKingdomName()) && !damager.hasPermission("FireKingdom.Staff")) {
                            event.setCancelled(true);
                            damager.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je kan niet iemand slaan in zijn eigen border!");
                        }
                    }
                }
            }
        }
    }
}
