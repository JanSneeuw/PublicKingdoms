package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.NotInKingomValidation;
import net.zwet.publickingdom.validation.Validator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KingdomloosJoin implements Listener {
    PublicKingdom plugin;

    public KingdomloosJoin(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        Playerdata playerdata = null;
        try{
            playerdata = new Playerdata(player);
        }catch (NullPointerException e){
            Bukkit.getLogger().warning("Kan " + player.getName() + "'s playerdata niet laden! {KingdomloosJoin: 17}");
        }
        if (playerdata != null){
            Validator validator = new Validator().addValidation(new NotInKingomValidation(playerdata));
            boolean passOn = validator.executeValidations();
            if (passOn){
                Bukkit.broadcastMessage(prefix + ChatColor.GRAY + "De speler " + ChatColor.WHITE + player.getName() + ChatColor.GRAY + " is nog '" +ChatColor.DARK_GREEN + "kingdomloos" + ChatColor.GRAY + "'");
            }
        }
    }
}
