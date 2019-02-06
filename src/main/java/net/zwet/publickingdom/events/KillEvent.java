package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class KillEvent implements Listener {
    PublicKingdom plugin;
    public KillEvent(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            Playerdata playerdata = new Playerdata(player);
            if (playerdata.exists()) {
                try {
                    playerdata.addKill();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
