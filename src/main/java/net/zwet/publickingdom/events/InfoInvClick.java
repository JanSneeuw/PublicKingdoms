package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class InfoInvClick implements Listener {
    PublicKingdom plugin;

    public InfoInvClick(PublicKingdom instance) {
        plugin = instance;
    }


    @EventHandler
    public void InvClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getInventory().getTitle().contains(ChatColor.GRAY + "INFO")) {
                if (event.getCurrentItem() != null) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
