package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class JoinEvent implements Listener {
    PublicKingdom plugin;

    public JoinEvent(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        FileConfiguration playerdata = null;
        File playerdatafile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + event.getPlayer().getUniqueId().toString() + ".yml");
        if (!playerdatafile.exists()) {
            try {
                playerdatafile.createNewFile();
                playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
                if (!(playerdata.getString("naam") == null) && !playerdata.equals(event.getPlayer().getName())) {
                    playerdata.set("naam", event.getPlayer().getName());
                } else {
                    playerdata.set("naam", event.getPlayer().getName());
                }
                playerdata.set("UUID", event.getPlayer().getUniqueId().toString());
                playerdata.set("kingdom", plugin.getConfig().get("Kingdomloos"));
                playerdata.set("rank", "Burger");
                playerdata.set("kills", 0);
                playerdata.set("deaths", 0);
                playerdata.set("IP", event.getPlayer().getAddress().toString());
                playerdata.set("scoreboard", "ON");
                playerdata.save(playerdatafile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
            playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
            if (playerdata.getString("naam") != null && !playerdata.getString("naam").equalsIgnoreCase(event.getPlayer().getName())) {
                    playerdata.set("naam", event.getPlayer().getName());
                try {
                    playerdata.save(playerdatafile);
                } catch (IOException e) {
                    Bukkit.getLogger().warning("Kan playerdata van " + event.getPlayer().getName() + " niet opslaan {JoinEvent.java: 51}");
                }
            }
            }
    }
}

