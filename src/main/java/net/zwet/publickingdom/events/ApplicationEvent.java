package net.zwet.publickingdom.events;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.commands.Application;
import net.zwet.publickingdom.custom_events.ApplicationSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApplicationEvent implements Listener {
    private Map<UUID, net.zwet.publickingdom.objects.Application> applications = Application.applicationMap;
    @EventHandler
    public void ApplicationChat(AsyncPlayerChatEvent event){
        if (applications.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            net.zwet.publickingdom.objects.Application application = applications.get(event.getPlayer().getUniqueId());
            if (!event.getMessage().equalsIgnoreCase("KLAAR")) {
                if (applications.get(event.getPlayer().getUniqueId()).getText() != null) {
                    application.addText(event.getMessage());
                }else {
                    application.setText(event.getMessage());
                }
            }else{
                if (application.getText() == null){
                    applications.remove(event.getPlayer().getUniqueId());
                    event.getPlayer().sendMessage("Je sollicitatie is gecancled omdat je niks hebt ingevoerd!");
                }else{
                    try {
                        application.send();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    event.getPlayer().sendMessage("Je hebt de volgende sollicitatie verstuurd: " + application.getText());
                    ApplicationSendEvent applicationEvent = new ApplicationSendEvent(application);
                    Bukkit.getPluginManager().callEvent(applicationEvent);
                    applications.remove(event.getPlayer().getUniqueId());
                }
            }
        }
    }
}
