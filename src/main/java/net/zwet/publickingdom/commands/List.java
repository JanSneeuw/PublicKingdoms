package net.zwet.publickingdom.commands;
import net.zwet.publickingdom.PublicKingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class List implements CommandExecutor {
    PublicKingdom plugin;

    public List(PublicKingdom instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File[] kingdomFiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
        File folder = new File(plugin.getDataFolder() + File.separator + "kingdoms");
        File playerfolder = new File(plugin.getDataFolder() + File.separator + "players");
        String[] files = folder.list();
        File[] playerfiles = playerfolder.listFiles();
        String[] playerfilesstring = playerfolder.list();
        YamlConfiguration kds = new YamlConfiguration();
        YamlConfiguration pls = new YamlConfiguration();
        Player player = (Player) sender;
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

        StringBuilder kingdomlijst = new StringBuilder();
        for (int i = 0; i < files.length; i++) {
            kingdomlijst.append(files[i]).append(" ");
        }
        String lijst = kingdomlijst.toString().trim();

        if (player instanceof Player) {
            player.sendMessage(prefix + ChatColor.WHITE + lijst.replace(".yml", ","));
        }
        if (Bukkit.getOnlinePlayers().size() == 1) {

            player.sendMessage(prefix + "§7Er is momenteel maar §f1 §7speler online: ");
            StringBuilder playerlijst = new StringBuilder();
            if (kingdomFiles != null) {
                for (File kingdoms : kingdomFiles) {
                    try {
                        kds.load(kingdoms);
                    } catch (IOException e) {
                        Bukkit.getLogger().warning("Kan kingdom files niet laden voor list!");
                    } catch (InvalidConfigurationException e) {
                        Bukkit.getLogger().warning("Kan kingdom files niet laden voor list!");
                    }
                    for (File players : playerfiles) {
                        try {
                            pls.load(players);
                        } catch (IOException e) {
                            Bukkit.getLogger().warning("Kan player files niet laden voor list!");
                        } catch (InvalidConfigurationException e) {
                            Bukkit.getLogger().warning("Kan player files niet laden voor list!");
                        }
                        if (pls.get("kingdom").equals(kds.get("naam"))) {
                            if (Bukkit.getPlayer(UUID.fromString(pls.getString("UUID"))) != null) {
                                playerlijst.append(pls.get("naam")).append("§7,§f ");
                            }
                        }
                    }

                    String plist = playerlijst.toString().trim();
                    if (plist.length() == 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', kds.get("prefix").toString() + "§7: " + ChatColor.DARK_GRAY + "/"));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GRAY + kds.get("prefix").toString() + "§7: " + ChatColor.WHITE + plist));
                    }
                    playerlijst.setLength(0);
                }
            }
        }


        if (Bukkit.getOnlinePlayers().size() > 1) {
            StringBuilder playerlijst = new StringBuilder();
            player.sendMessage(prefix + "Er zijn momenteel §f" + Bukkit.getOnlinePlayers().size() + "§7 spelers online: ");
            if (kingdomFiles != null) {
                for (File kingdoms : kingdomFiles) {
                    try {
                        kds.load(kingdoms);
                    } catch (IOException e) {
                        Bukkit.getLogger().warning("Kan kingdom files niet laden voor list!");
                    } catch (InvalidConfigurationException e) {
                        Bukkit.getLogger().warning("Kan kingdom files niet laden voor list!");
                    }
                    for (File players : playerfiles) {
                        try {
                            pls.load(players);
                        } catch (IOException e) {
                            Bukkit.getLogger().warning("Kan player files niet laden voor list!");
                        } catch (InvalidConfigurationException e) {
                            Bukkit.getLogger().warning("Kan player files niet laden voor list!");
                        }
                        if (pls.get("kingdom").equals(kds.get("naam"))) {
                            if (Bukkit.getPlayer(UUID.fromString(pls.getString("UUID"))) != null) {
                                playerlijst.append(pls.get("naam")).append("§7,§f ");
                            }
                        }
                    }

                    String plist = playerlijst.toString().trim();
                    if (plist.length() == 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', kds.get("prefix").toString() + "§7: " + ChatColor.DARK_GRAY + "/"));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GRAY + kds.get("prefix").toString() + "§7: " + ChatColor.WHITE + plist));
                    }
                    playerlijst.setLength(0);
                }
            }
        }


        return false;
    }
}
