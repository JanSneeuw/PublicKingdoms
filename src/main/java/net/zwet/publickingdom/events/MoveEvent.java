package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.commands.Spawn;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
    PublicKingdom plugin;

    public MoveEvent(PublicKingdom instance) {
        plugin = instance;
    }
    @EventHandler
    public void playemove(PlayerMoveEvent event){
        if (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockY() == event.getFrom().getBlockY() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) return;
            if (Spawn.hspawner.containsKey(event.getPlayer())){
                String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                event.getPlayer().sendMessage(prefix + " " +  ChatColor.GRAY+ " Door te bewegen heb je heb je de teleportatie gecancelled!");
                Spawn.hspawner.get(event.getPlayer()).cancel();
                Spawn.hspawner.remove(event.getPlayer());
            }
    }
}
