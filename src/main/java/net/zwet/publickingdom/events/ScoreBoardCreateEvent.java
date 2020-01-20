package net.zwet.publickingdom.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ScoreBoardCreateEvent implements Listener {
    PublicKingdom plugin;

    public ScoreBoardCreateEvent(PublicKingdom instance) {
        plugin = instance;
    }

    public static ScoreboardManager manager = Bukkit.getScoreboardManager();
    public static Map<Player, Scoreboard> scoreboardMap = new HashMap<>();
    public static Map<Player, Team> teamHashMap = new HashMap<>();
    public static Map<Player, ProtectedRegion> regionHashMap = new HashMap<>();

    @EventHandler
    public void sbc(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        if (playerdata.exists()) {
            Kingdom kingdom = new Kingdom(player);
            File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
            YamlConfiguration kds = new YamlConfiguration();

            Scoreboard board = manager.getNewScoreboard();
            if (playerdata.boardIsOn()) {
                Objective objective = board.registerNewObjective("PublicKingdom", "dummy", "PublicKingdom");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Scoreboard-Title")));
                Score kingdoms = objective.getScore(ChatColor.RED + "Kingdom:");
                kingdoms.setScore(20);
                if (!playerdata.isInKingdom()) {
                    Score kingdomR = objective.getScore(ChatColor.WHITE + "GEEN ");
                    kingdomR.setScore(19);
                    Score blankSpot = objective.getScore(ChatColor.WHITE + "  ");
                    blankSpot.setScore(18);
                    Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                    Rank.setScore(17);
                    Score RankR = objective.getScore(ChatColor.WHITE + "GEEN");
                    RankR.setScore(16);
                } else {
                    Score kingdomR = objective.getScore(ChatColor.WHITE + playerdata.getKingdomName());
                    kingdomR.setScore(19);
                    Score blankSpot = objective.getScore(ChatColor.WHITE + "  ");
                    blankSpot.setScore(18);
                    Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                    Rank.setScore(17);
                    Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                    RankR.setScore(16);
                    Score blankSpot2 = objective.getScore(ChatColor.WHITE + "   ");
                    blankSpot2.setScore(15);
                    Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                    spot.setScore(11);
                    LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);
                    RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = regionContainer.createQuery();

                    if (query.getApplicableRegions(lplayer.getLocation()).size() == 0) {
                        Score locResult = objective.getScore(ChatColor.WHITE + "???");
                        locResult.setScore(10);

                    } else {
                        ApplicableRegionSet set = query.getApplicableRegions(lplayer.getLocation());
                        for (ProtectedRegion kingdomRegion : set) {
                            Score spotResult = objective.getScore(ChatColor.WHITE + kingdomRegion.getId());
                            spotResult.setScore(10);
                        }
                    }
                    if (kingdom.getKing() != null) {
                        Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                        blankSpot3.setScore(12);
                        Score king = objective.getScore(ChatColor.RED + "Koning:");
                        king.setScore(14);
                        Score kingResult = objective.getScore(ChatColor.WHITE + kingdom.getKing().getName());
                        kingResult.setScore(13);
                    }else{
                        Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                        blankSpot3.setScore(12);
                        Score king = objective.getScore(ChatColor.RED + "Koning:");
                        king.setScore(14);
                        Score kingResult = objective.getScore("GEEN");
                        kingResult.setScore(13);
                    }
                }
            }
            if (kdfiles != null) {
                for (File kingdoms : kdfiles) {
                    try {
                        kds.load(kingdoms);
                    } catch (IOException e) {
                        Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                    } catch (InvalidConfigurationException e) {
                        Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                    }
                    if (kds.get("naam") != null && board.getTeam(kds.get("naam").toString()) == null) {
                        board.registerNewTeam(kds.get("naam").toString());
                    }
                    board.getTeam(kds.get("naam").toString()).setAllowFriendlyFire(false);
                    if (!kds.getString("team-prefix").equalsIgnoreCase("NONE")) {
                        board.getTeam(kds.get("naam").toString()).setPrefix(ChatColor.translateAlternateColorCodes('&',kds.getString("team-prefix")));
                    }
                    if (!kds.getString("name-color").equalsIgnoreCase("NONE")) {
                        board.getTeam(kds.get("naam").toString()).setColor(ChatColor.getByChar(kds.getString("name-color").replace('&', '§')));
                    }
                }

            }
            for (Player p : teamHashMap.keySet()) {
                board.getTeam(teamHashMap.get(p).getName()).addEntry(p.getName());
            }
            if (playerdata.isInKingdom()) {
                board.getTeam(playerdata.getKingdomName()).addEntry(player.getName());
                teamHashMap.put(player, board.getTeam(playerdata.getKingdomName()));
            }
            for (Player cpa : Bukkit.getOnlinePlayers()) {
                if (!cpa.getName().equals(player.getName())) {
                    if (playerdata.isInKingdom()) {
                        Scoreboard scoreboard = scoreboardMap.get(cpa);
                        if (scoreboard.getTeam(playerdata.getKingdomName()) != null) {
                            scoreboard.getTeam(playerdata.getKingdomName()).addEntry(player.getName());
                        }
                    }
                }
            }

            player.setScoreboard(board);
            scoreboardMap.put(player, board);
        }
    }

    @EventHandler
    public void RegionChange(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        if (playerdata.exists()) {
            File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
            YamlConfiguration kds = new YamlConfiguration();
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective("PublicKingdom", "dummy", "PublicKingdom");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Scoreboard-Title")));
            if (playerdata.isInKingdom()) {
                if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockY() != event.getFrom().getBlockY() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
                    Location from = event.getFrom();
                    Location to = event.getTo();
                    RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionQuery query = regionContainer.createQuery();
                    if (query.getApplicableRegions(BukkitAdapter.adapt(to)).size() == 0 && regionHashMap.get(player) != null) {
                        regionHashMap.remove(player);
                        if (playerdata.boardIsOn()) {
                            Score kingdoms = objective.getScore(ChatColor.RED + "Kingdom:");
                            kingdoms.setScore(20);
                            Score kingdomR = objective.getScore(ChatColor.WHITE + playerdata.getKingdomName());
                            kingdomR.setScore(19);
                            Score blankSpot = objective.getScore(ChatColor.WHITE + "  ");
                            blankSpot.setScore(18);
                            Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                            Rank.setScore(17);
                            Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                            RankR.setScore(16);
                            Score blankSpot2 = objective.getScore(ChatColor.WHITE + "   ");
                            blankSpot2.setScore(15);
                            Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                            spot.setScore(11);
                            Score spotR = objective.getScore(ChatColor.WHITE + "???");
                            spotR.setScore(10);
                            Kingdom kingdom = new Kingdom(player);
                            if (kingdom.getKing() != null) {
                                if (kingdom.getKing().exists()) {
                                    Playerdata kingdata = kingdom.getKing();
                                    if (kingdata.exists()) {
                                        Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                        blankSpot3.setScore(12);
                                        Score king = objective.getScore(ChatColor.RED + "Koning:");
                                        king.setScore(14);
                                        Score kingResult = objective.getScore(ChatColor.WHITE + kingdata.getName());
                                        kingResult.setScore(13);
                                    }
                                }
                            }else{
                                Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                blankSpot3.setScore(12);
                                Score king = objective.getScore(ChatColor.RED + "Koning:");
                                king.setScore(14);
                                Score kingResult = objective.getScore(ChatColor.WHITE + "GEEN");
                                kingResult.setScore(13);
                            }
                        }
                        if (kdfiles != null) {
                            for (File kingdoms : kdfiles) {
                                try {
                                    kds.load(kingdoms);
                                } catch (IOException | InvalidConfigurationException e) {
                                    Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                }
                                if (kds.get("naam") != null && board.getTeam(kds.get("naam").toString()) == null) {
                                    board.registerNewTeam(kds.get("naam").toString());
                                }
                                board.getTeam(kds.get("naam").toString()).setAllowFriendlyFire(false);
                                if (!kds.getString("team-prefix").equalsIgnoreCase("NONE")) {
                                    board.getTeam(kds.get("naam").toString()).setPrefix(ChatColor.translateAlternateColorCodes('&',kds.getString("team-prefix")));
                                }
                                if (!kds.getString("name-color").equalsIgnoreCase("NONE")) {
                                    board.getTeam(kds.get("naam").toString()).setColor(ChatColor.getByChar(kds.getString("name-color").replace('&', '§')));
                                }
                            }
                            if (playerdata.isInKingdom()) {
                                board.getTeam(playerdata.getKingdomName()).addEntry(player.getName());
                                teamHashMap.put(player, board.getTeam(playerdata.getKingdomName()));
                            }
                        }

                        for (Player p : teamHashMap.keySet()) {
                            board.getTeam(teamHashMap.get(p).getName()).addEntry(p.getName());
                        }


                        player.setScoreboard(board);
                        scoreboardMap.put(player, board);
                    } else if (query.getApplicableRegions(BukkitAdapter.adapt(to)).size() != 0 && regionHashMap.get(player) == null) {
                        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(to));
                        for (ProtectedRegion region : set) {
                            regionHashMap.put(player, region);
                            if (playerdata.boardIsOn()) {
                                Score kingdoms = objective.getScore(ChatColor.RED + "Kingdom:");
                                kingdoms.setScore(20);
                                Score kingdomR = objective.getScore(ChatColor.WHITE + playerdata.getKingdomName());
                                kingdomR.setScore(19);
                                Score blankSpot = objective.getScore(ChatColor.WHITE + "  ");
                                blankSpot.setScore(18);
                                Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                                Rank.setScore(17);
                                Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                                RankR.setScore(16);
                                Score blankSpot2 = objective.getScore(ChatColor.WHITE + "   ");
                                blankSpot2.setScore(15);
                                Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                                spot.setScore(11);
                                Score spotR = objective.getScore(ChatColor.WHITE + region.getId().replaceAll("new-rhean", "§fNew-Rhean").replaceAll("katakinos", "§fKatakinos").replaceAll("spawn", "§fSpawn").replaceAll("ashanti", "§fAshanti").replaceAll("kayantos", "§fKayantos").replaceAll("tyros", "§fTyros").replaceAll("wellcliff", "§fWellcliff").replaceAll("peacevillage", "§fPeaceVillage").replaceAll("lumbridge", "§fLumbridge").replaceAll("zetios", "§fZetios").replaceAll("ziladia", "§fZiladia"));
                                spotR.setScore(10);
                                Kingdom kingdom = new Kingdom(player);
                                if (kingdom.exists()) {
                                    if (kingdom.getKing() != null) {
                                        Playerdata kingdata = kingdom.getKing();
                                        if (kingdata.exists()) {
                                            Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                            blankSpot3.setScore(12);
                                            Score king = objective.getScore(ChatColor.RED + "Koning:");
                                            king.setScore(14);
                                            Score kingResult = objective.getScore(ChatColor.WHITE + kingdata.getName());
                                            kingResult.setScore(13);
                                        }
                                    }else{
                                        Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                        blankSpot3.setScore(12);
                                        Score king = objective.getScore(ChatColor.RED + "Koning:");
                                        king.setScore(14);
                                        Score kingResult = objective.getScore("GEEN");
                                        kingResult.setScore(13);
                                    }
                                }
                            }
                            if (kdfiles != null) {
                                for (File kingdoms : kdfiles) {
                                    try {
                                        kds.load(kingdoms);
                                    } catch (IOException e) {
                                        Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                    } catch (InvalidConfigurationException e) {
                                        Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                    }
                                    board.registerNewTeam(kds.get("naam").toString());
                                    board.getTeam(kds.get("naam").toString()).setAllowFriendlyFire(false);
                                    if (!kds.getString("team-prefix").equalsIgnoreCase("NONE")) {
                                        board.getTeam(kds.get("naam").toString()).setPrefix(ChatColor.translateAlternateColorCodes('&',kds.getString("team-prefix")));
                                    }
                                    if (!kds.getString("name-color").equalsIgnoreCase("NONE")) {
                                        board.getTeam(kds.get("naam").toString()).setColor(ChatColor.getByChar(kds.getString("name-color").replace('&', '§')));
                                    }
                                }
                                if (playerdata.isInKingdom()) {
                                    board.getTeam(playerdata.getKingdomName()).addEntry(player.getName());
                                    teamHashMap.put(player, board.getTeam(playerdata.getKingdomName()));
                                }
                            }

                            for (Player p : teamHashMap.keySet()) {
                                board.getTeam(teamHashMap.get(p).getName()).addEntry(p.getName());
                            }

                            player.setScoreboard(board);
                            scoreboardMap.put(player, board);
                        }
                    } else if (query.getApplicableRegions(BukkitAdapter.adapt(to)).size() != 0 && regionHashMap.get(player) != null) {
                        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(to));
                        for (ProtectedRegion region : set) {
                            if (!regionHashMap.get(player).equals(region)) {
                                regionHashMap.put(player, region);
                                if (playerdata.boardIsOn()) {
                                    Score kingdoms = objective.getScore(ChatColor.RED + "Kingdom:");
                                    kingdoms.setScore(20);
                                    Score kingdomR = objective.getScore(ChatColor.WHITE + playerdata.getKingdomName());
                                    kingdomR.setScore(19);
                                    Score blankSpot = objective.getScore(ChatColor.WHITE + "  ");
                                    blankSpot.setScore(18);
                                    Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                                    Rank.setScore(17);
                                    Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                                    RankR.setScore(16);
                                    Score blankSpot2 = objective.getScore(ChatColor.WHITE + "   ");
                                    blankSpot2.setScore(15);
                                    Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                                    spot.setScore(11);
                                    Score spotR = objective.getScore(ChatColor.WHITE + region.getId().replaceAll("new-rhean", "§fNew-Rhean").replaceAll("katakinos", "§fKatakinos").replaceAll("spawn", "§fSpawn").replaceAll("ashanti", "§fAshanti").replaceAll("kayantos", "§fKayantos").replaceAll("tyros", "§fTyros").replaceAll("wellcliff", "§fWellcliff").replaceAll("peacevillage", "§fPeaceVillage").replaceAll("lumbridge", "§fLumbridge").replaceAll("zetios", "§fZetios").replaceAll("ziladia", "§fZiladia"));
                                    spotR.setScore(10);
                                    Kingdom kingdom = new Kingdom(player);
                                    if (kingdom.exists()) {
                                        Playerdata kingdata = kingdom.getKing();
                                        if (kingdata.exists()) {
                                            Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                            blankSpot3.setScore(12);
                                            Score king = objective.getScore(ChatColor.RED + "Koning:");
                                            king.setScore(14);
                                            Score kingResult = objective.getScore(ChatColor.WHITE + kingdata.getName());
                                            kingResult.setScore(13);
                                        }
                                    }
                                }
                                if (kdfiles != null) {
                                    for (File kingdoms : kdfiles) {
                                        try {
                                            kds.load(kingdoms);
                                        } catch (IOException e) {
                                            Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                        } catch (InvalidConfigurationException e) {
                                            Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                                        }
                                        if (!board.getTeams().contains(board.getTeam(kds.getString("naam")))) {
                                            board.registerNewTeam(kds.get("naam").toString());
                                            board.getTeam(kds.get("naam").toString()).setAllowFriendlyFire(false);
                                            if (!kds.getString("team-prefix").equalsIgnoreCase("NONE")) {
                                                board.getTeam(kds.get("naam").toString()).setPrefix(ChatColor.translateAlternateColorCodes('&',kds.getString("team-prefix")));
                                            }
                                            if (!kds.getString("name-color").equalsIgnoreCase("NONE")) {
                                                board.getTeam(kds.get("naam").toString()).setColor(ChatColor.getByChar(kds.getString("name-color").replace('&', '§')));
                                            }}
                                    }
                                    if (playerdata.isInKingdom()) {
                                        board.getTeam(playerdata.getKingdomName()).addEntry(player.getName());
                                        teamHashMap.put(player, board.getTeam(playerdata.getKingdomName()));
                                    }
                                }

                                for (Player p : teamHashMap.keySet()) {
                                    board.getTeam(teamHashMap.get(p).getName()).addEntry(p.getName());
                                }

                                player.setScoreboard(board);
                                scoreboardMap.put(player, board);
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Playerdata playerdata = new Playerdata(player);
        if (playerdata.isInKingdom()){
            if (teamHashMap.containsKey(player)){
                teamHashMap.remove(player);
                for (Player cpa : Bukkit.getOnlinePlayers()) {
                    if (!cpa.getName().equalsIgnoreCase(player.getName())) {
                        Scoreboard cpascoreboard = cpa.getScoreboard();
                        cpascoreboard.getTeam(playerdata.getKingdomName()).removeEntry(player.getName());
                    }
                }
            }
        }
    }
}
