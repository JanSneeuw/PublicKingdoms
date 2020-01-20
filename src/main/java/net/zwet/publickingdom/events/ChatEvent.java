package net.zwet.publickingdom.events;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ChatEvent implements Listener {
    PublicKingdom plugin;

    public ChatEvent(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void Chat(AsyncPlayerChatEvent event) {
        ArrayList<Player> send = new ArrayList<Player>();
        YamlConfiguration kds = new YamlConfiguration();
        Player player = event.getPlayer();
        File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "players").listFiles();
        Playerdata playerdata = new Playerdata(player);
        FileConfiguration mutedata = null;
        File mutedatafile = new File("plugins/Essentials/userdata" + File.separator + player.getUniqueId().toString() + ".yml");
        mutedata = YamlConfiguration.loadConfiguration(mutedatafile);
        String message = event.getMessage().trim();
        String Rank = kds.getString("players." + event.getPlayer().getUniqueId().toString() + ".rank");
        Player p = event.getPlayer();
        PermissionUser puser = PermissionsEx.getUser(player);
        String prefix = PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix();
        if (playerdata.isInKingdom()) {
            Kingdom kingdom = new Kingdom(player);
            if (event.getMessage().charAt(0) == '!') {
                if (!player.hasPermission("publickingdom.staffcolor")) {
                    event.setFormat(ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "]" + "[" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "] " + ChatColor.RESET + player.getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix()
                            + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + event.getMessage().replace("!", "").replace("&", "§").replace("%", "‰"));
                }else{
                    event.setFormat(ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "]" + "[" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "] " + ChatColor.RESET + player.getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix()
                            + ChatColor.WHITE + ":" + ChatColor.WHITE + " " + event.getMessage().replace("!", "").replace("&", "§").replace("%", "‰"));
                }
            } else if (event.getMessage().charAt(0) == '$') {
                event.setFormat(ChatColor.RED + "T" + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "]" + "[" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "] " + ChatColor.RESET + player.getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix()
                        + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + event.getMessage().replace("$", "").replace("&", "§").replace("%", "‰"));
            }else if (event.getMessage().charAt(0) == '#') {
                event.setFormat(ChatColor.RED + "R" + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "]" + "[" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "] " + ChatColor.RESET + player.getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix()
                        + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + event.getMessage().replace("#", "").replace("&", "§").replace("%", "‰"));
            }else{
                for (Player oplayer : Bukkit.getOnlinePlayers()) {
                    if (!oplayer.getName().equals(player.getName())) {
                        event.getRecipients().remove(oplayer);
                    }
                }
                for (File files : kdfiles) {
                    try {
                        kds.load(files);
                        if (playerdata.isInKingdom()) {
                            if (!(event.getMessage().charAt(0) == '!')) {
                                if (kds.get("kingdom").equals(playerdata.getKingdomName())) {

                                    Player pl = Bukkit.getPlayer(UUID.fromString(kds.getString("UUID")));
                                    if (pl != null) {
                                        event.getRecipients().add(pl);
                                        event.setFormat(ChatColor.GREEN + " §2K " + ChatColor.WHITE + "[" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.RESET + " " + event.getPlayer().getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix() + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + event.getMessage().replace("%", "‰"));
                                    }
                                }
                            }
                        }

                    } catch (InvalidConfigurationException | IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        } else if (!playerdata.isInKingdom()) {
            if (!player.hasPermission("kingdom.staffcolor")){
                event.setFormat(ChatColor.WHITE + "§7[§2Kingdomloos" + "§7] " + ChatColor.RESET + event.getPlayer().getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix() + ChatColor.WHITE + ":" + ChatColor.GRAY + " " + event.getMessage().replace("%", "‰").replace("!", ""));
            }else {
                event.setFormat(ChatColor.WHITE + "§7[§2Kingdomloos" + "§7] " + ChatColor.RESET + event.getPlayer().getDisplayName() + PermissionsEx.getPermissionManager().getGroup(p.getName()).getPrefix() + ChatColor.WHITE + ":" + ChatColor.WHITE + " " + event.getMessage().replace("%", "‰").replace("!", ""));
            }
        }
    }
}

