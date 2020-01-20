package net.zwet.publickingdom.custom_events;

import net.zwet.publickingdom.objects.Application;
import net.zwet.publickingdom.objects.Kingdom;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ApplicationSendEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Application application;
    public ApplicationSendEvent(Application application) {
        this.application = application;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    public Application getApplication(){
        return application;
    }

    public Player getPlayer(){
        return application.getPlayer();
    }

    public String getMessage(){
        return application.getText();
    }

    public Kingdom getKingdom(){
        return application.getKingdom();
    }
}
