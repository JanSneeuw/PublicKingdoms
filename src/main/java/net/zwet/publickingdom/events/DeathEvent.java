package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class DeathEvent implements Listener {

    PublicKingdom plugin;
    public DeathEvent(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = event.getEntity();
            Playerdata playerdata = new Playerdata(player);
            if (playerdata.exists()) {
                try {
                    playerdata.addDeath();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

