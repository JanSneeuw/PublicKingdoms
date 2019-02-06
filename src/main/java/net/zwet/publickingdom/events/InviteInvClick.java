package net.zwet.publickingdom.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.commands.Invite;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;

public class InviteInvClick implements Listener {
    PublicKingdom plugin;

    public InviteInvClick(PublicKingdom instance) {
        plugin = instance;
    }


    @EventHandler
    public void InvClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Playerdata playerdata = new Playerdata(player);
            Scoreboard board = ScoreBoardCreateEvent.manager.getNewScoreboard();
            if (event.getInventory().getTitle().contains("§7- " + ChatColor.GRAY + "Invite")) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null) {
                    ItemStack Clicked = event.getCurrentItem();
                    if (Clicked.hasItemMeta()) {
                        if (!playerdata.isInKingdom()) {
                            if (Clicked.getItemMeta().getDisplayName().contains("Join")) {
                                if (Invite.invites.contains(player.getName() + " " + Clicked.getItemMeta().getDisplayName().split(" ")[1])) {
                                    File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
                                    YamlConfiguration kds = new YamlConfiguration();
                                    String Kingdom = Clicked.getItemMeta().getDisplayName().split(" ")[1];
                                    Kingdom kingdom = null;
                                    try {
                                        kingdom = new Kingdom(Kingdom);
                                    } catch (NoSuchKingdomException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        playerdata.setKingdom(Kingdom);
                                        playerdata.setRank(kingdom.getDefaultRank());
                                    } catch (IOException e) {
                                        Bukkit.getLogger().warning("Kan playerdata niet saven {InviteInvClick: 56}");
                                    }

                                    if (playerdata.boardIsOn()) {
                                        Objective objective = board.registerNewObjective("FireKingdom", "dummy");
                                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                                        objective.setDisplayName(ChatColor.WHITE + "   " + ChatColor.RED + ChatColor.BOLD + "Fire" + ChatColor.YELLOW + ChatColor.BOLD + "Kingdom" + ChatColor.RESET + ChatColor.WHITE + "   ");

                                        Score kingdomS = objective.getScore(ChatColor.RED + "kingdom:");
                                        kingdomS.setScore(20);
                                        Score kingdomR = objective.getScore(ChatColor.WHITE + playerdata.getKingdomName());
                                        kingdomR.setScore(19);
                                        Score blankSpot = objective.getScore(ChatColor.RED + " ");
                                        blankSpot.setScore(18);
                                        Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                                        Rank.setScore(17);
                                        Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                                        RankR.setScore(16);
                                        Score blankSpot2 = objective.getScore(ChatColor.GRAY + "  ");
                                        blankSpot2.setScore(15);
                                        Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                                        spot.setScore(14);
                                        if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).getRegions().size() == 0) {
                                            Score locResult = objective.getScore(ChatColor.WHITE + "???");
                                            locResult.setScore(13);

                                        } else {
                                            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
                                                Score spotResult = objective.getScore(ChatColor.WHITE + kingdomRegion.getId().replaceAll("new-rhean", "§fNew-Rhean").replaceAll("katakinos", "§fKatakinos").replaceAll("spawn", "§fSpawn").replaceAll("ashanti", "§fAshanti").replaceAll("kayantos", "§fKayantos").replaceAll("tyros", "§fTyros").replaceAll("wellcliff", "§fWellcliff").replaceAll("peacevillage", "§fPeaceVillage").replaceAll("lumbridge", "§fLumbridge").replaceAll("zetios", "§fZetios").replaceAll("ziladia", "§fZiladia"));
                                                spotResult.setScore(13);
                                            }
                                        }
                                        if (kingdom.getKing() != null) {
                                            Playerdata kingdata = kingdom.getKing();
                                            if (kingdata.exists()) {
                                                Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                                blankSpot3.setScore(12);
                                                Score king = objective.getScore(ChatColor.RED + "Koning:");
                                                king.setScore(11);
                                                Score kingResult = objective.getScore(ChatColor.WHITE + kingdata.getName());
                                                kingResult.setScore(10);
                                            }
                                        }
                                    }
                                    for (Player playerse : Bukkit.getOnlinePlayers()) {
                                        if (!playerse.getName().equals(player.getName())) {
                                            Scoreboard playersescoreboard = playerse.getScoreboard();
                                            playersescoreboard.getTeam(Kingdom).addEntry(player.getName());
                                        }
                                    }
                                    if (kdfiles != null) {
                                        for (File kingdoms : kdfiles){
                                            try {
                                                kds.load(kingdoms);
                                            } catch (IOException e) {
                                                Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                            } catch (InvalidConfigurationException e) {
                                                Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                            }
                                            board.registerNewTeam(kds.get("naam").toString());
                                            board.getTeam(kds.get("naam").toString()).setAllowFriendlyFire(false);
                                            board.getTeam(kds.get("naam").toString()).setPrefix(kds.get("prefix-color").toString().replace('&', '§'));
                                        }
                                    }
                                    board.getTeam(Kingdom).addEntry(player.getName());
                                    ScoreBoardCreateEvent.teamHashMap.put(player, board.getTeam(Kingdom));
                                    for (Player p : ScoreBoardCreateEvent.teamHashMap.keySet()) {
                                        board.getTeam(ScoreBoardCreateEvent.teamHashMap.get(p).getName()).addEntry(p.getName());
                                    }
                                    player.setScoreboard(board);
                                    ScoreBoardCreateEvent.scoreboardMap.put(player, board);

                                    Invite.invites.remove(player + " " + Kingdom);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString())+ "§7Je zit nu in het kingdom§f " + Kingdom + "§7!");
                                    player.closeInventory();
                                    Location spawnloc = kingdom.getSpawn();
                                    player.teleport(spawnloc);
                                }
                            } else if (Clicked.getItemMeta().getDisplayName().contains("Weiger")) {
                                player.closeInventory();
                                YamlConfiguration kds = new YamlConfiguration();
                                String Kingdom = Clicked.getItemMeta().getDisplayName().split(" ")[1];
                                player.sendMessage( "§c§lFire§e§lKingdom §7- U heeft zojuist de invite geweigerd! ");
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
