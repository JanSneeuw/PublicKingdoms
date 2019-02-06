package net.zwet.publickingdom.events;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Respawn implements Listener {
    PublicKingdom plugin;

    public Respawn(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void RespawnEvent(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        if (!playerdata.isInKingdom()){
            double x = -79.527; //This is a coordinate I tried with
            double y = 34; //This is a coordinate I tried with
            double z = 236.518; //This is a coordinate I tried with
            World w = Bukkit.getWorld(plugin.getConfig().get("kingdomloos-spawn").toString());

            Location l = new Location(w, x, y, z, -92.6F, -0.3F); //Try location
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.teleport(l);
                }
            }.runTaskLater(plugin, 1);
        }
        else {
            Kingdom kingdom = new Kingdom(player);
            World world = Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"));
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.teleport(kingdom.getSpawn());
                }
            }.runTaskLater(plugin, 1);
        }
    }
}

