package net.zwet.publickingdom.events;

import net.zwet.publickingdom.Exceptions.NoSuchRankInKingdom;
import net.zwet.publickingdom.custom_events.ApplicationSendEvent;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ApplicationSend implements Listener {

    @EventHandler
    public void applicationSend(ApplicationSendEvent event){
        for (Player players : Bukkit.getOnlinePlayers()) {
            Playerdata playerdata = new Playerdata(players);
            if (playerdata.isInKingdom()) {
                if (playerdata.getKingdom() == event.getKingdom()) {
                    try {
                        Rank rank = new Rank(playerdata.getRank(), playerdata.getKingdom());
                        if (rank.seeApps()){
                            players.sendMessage("There is a new application from: " + event.getPlayer().getName());
                        }
                    } catch (NoSuchRankInKingdom noSuchRankInKingdom) {
                        noSuchRankInKingdom.printStackTrace();
                    }
                }
            }
        }
    }
}
