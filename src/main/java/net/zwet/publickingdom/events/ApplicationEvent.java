package net.zwet.publickingdom.events;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.commands.Application;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApplicationEvent implements Listener {
    public static Map<UUID, String> applicationMap = new HashMap<>();
    @EventHandler
    public void ApplicationChat(AsyncPlayerChatEvent event){
        if (Application.applicationCreator.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (!event.getMessage().equalsIgnoreCase("KLAAR")) {
                if (applicationMap.containsKey(event.getPlayer().getUniqueId())) {
                    applicationMap.put(event.getPlayer().getUniqueId(), applicationMap.get(event.getPlayer().getUniqueId()) + " " + event.getMessage());
                } else {
                    applicationMap.put(event.getPlayer().getUniqueId(), event.getMessage());
                }
            }else{
                if (!applicationMap.containsKey(event.getPlayer().getUniqueId())){
                    Application.applicationCreator.remove(event.getPlayer().getUniqueId());
                    event.getPlayer().sendMessage("Je sollicitatie is gecancled omdat je niks hebt ingevoerd!");
                }else{
                    try {
                        Application.applicationCreator.get(event.getPlayer().getUniqueId()).addApplication(event.getPlayer(), applicationMap.get(event.getPlayer().getUniqueId()));
                    } catch (NoSuchKingdomException e) {
                        return;
                    }
                    applicationMap.remove(event.getPlayer().getUniqueId());
                    Application.applicationCreator.remove(event.getPlayer().getUniqueId());
                    event.getPlayer().sendMessage("Je hebt sollicitatie sollicitatie verstuurd!");
                }
            }
        }
    }
}
