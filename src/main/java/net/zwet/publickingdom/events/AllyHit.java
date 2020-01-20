package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AllyHit implements Listener {
    PublicKingdom plugin;
    public AllyHit(PublicKingdom instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            Playerdata damagerdata = new Playerdata(damager);
            Playerdata damageddata = new Playerdata(damaged);
            Kingdom kingdom = new Kingdom(damager);
            if (damagerdata.isInKingdom() && damageddata.isInKingdom()){
                Kingdom dkingdom = new Kingdom(damaged);
                if (kingdom.isAllyWith(dkingdom)){
                    event.setCancelled(true);
                }
            }
        }else if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player){
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player){
                Player damager = (Player) arrow.getShooter();
                Player damaged = (Player) event.getEntity();

                Playerdata damagerdata = new Playerdata(damager);
                Playerdata damageddata = new Playerdata(damaged);
                Kingdom kingdom = new Kingdom(damager);
                String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                if (damagerdata.isInKingdom() && damageddata.isInKingdom()){
                    Kingdom dkingdom = new Kingdom(damaged);
                    if (kingdom.isAllyWith(dkingdom)){
                        event.setCancelled(true);
                    }
                }
            }
        }else if (event.getDamager() instanceof FishHook && event.getEntity() instanceof Player){
            FishHook fishHook = (FishHook) event.getDamager();
            if(fishHook.getShooter() instanceof Player){
                Player damager = (Player) fishHook.getShooter();
                Player damaged = (Player) event.getEntity();

                Playerdata damagerdata = new Playerdata(damager);
                Playerdata damageddata = new Playerdata(damaged);
                Kingdom kingdom = new Kingdom(damager);
                if (damagerdata.isInKingdom() && damageddata.isInKingdom()){
                    Kingdom dkingdom = new Kingdom(damaged);
                    if (kingdom.isAllyWith(dkingdom)){
                        event.setCancelled(true);
                    }
                }
            }
        }else if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            Snowball snowball = (Snowball) event.getDamager();
            if (snowball.getShooter() instanceof Player) {
                Player damager = (Player) snowball.getShooter();
                Player damaged = (Player) event.getEntity();

                Playerdata damagerdata = new Playerdata(damager);
                Playerdata damageddata = new Playerdata(damaged);
                Kingdom kingdom = new Kingdom(damager);
                if (damagerdata.isInKingdom() && damageddata.isInKingdom()) {
                    Kingdom dkingdom = new Kingdom(damaged);
                    if (kingdom.isAllyWith(dkingdom)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
