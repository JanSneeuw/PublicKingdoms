package net.zwet.publickingdom.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.events.ScoreBoardCreateEvent;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;

public class Setrank implements CommandExecutor {
    PublicKingdom plugin;

    public Setrank(PublicKingdom instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        if (args.length == 2) {
            //noinspection deprecation
            if (Bukkit.getPlayer(args[0]) != null){
                @SuppressWarnings("deprecation")
                Player ranked = Bukkit.getPlayer(args[0]);
                Playerdata playerdata = new Playerdata(player);
                Playerdata rankeddata = new Playerdata(ranked);
                if (playerdata.isInKingdom()) {
                    Kingdom kingdom = new Kingdom(player);
                    Scoreboard board = net.zwet.publickingdom.events.ScoreBoardCreateEvent.manager.getNewScoreboard();

                    if (rankeddata.isInKingdom()) {
                        Kingdom rankedkingdom = new Kingdom(ranked);
                        if (kingdom.getName().equalsIgnoreCase(rankedkingdom.getName()) || player.hasPermission("FireKingdom.Staff")) {
                            if (playerdata.hasPermission("k.setrank") || player.hasPermission("FireKingdom.Staff")) {
                                if (kingdom.hasRank(args[1])) {
                                    if ((playerdata.isHigherThan(args[1]) && !playerdata.getRank().equalsIgnoreCase(args[1])) || player.hasPermission("FireKingdom.Staff")) {
                                        if (!kingdom.rankLimitReached(args[1])) {
                                            File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
                                            YamlConfiguration kds = new YamlConfiguration();
                                            try {
                                                rankeddata.setRank(args[1]);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            ranked.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je bent nu §f" + args[1] + "§7!");
                                            player.sendMessage(fireprefix + " " + ChatColor.WHITE + ranked.getName() + " §7is nu §f" + args[1] + "§7!");

                                            if (rankeddata.boardIsOn()) {
                                                Objective objective = board.registerNewObjective("FireKingdom", "dummy");
                                                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                                                objective.setDisplayName(ChatColor.WHITE + "   " + ChatColor.RED + ChatColor.BOLD + "Fire" + ChatColor.YELLOW + ChatColor.BOLD + "Kingdom" + ChatColor.RESET + ChatColor.WHITE + "   ");

                                                Score k = objective.getScore(ChatColor.RED + "Kingdom:");
                                                k.setScore(20);
                                                Score kingdomR = objective.getScore(ChatColor.WHITE + rankeddata.getKingdomName());
                                                kingdomR.setScore(19);
                                                Score blankSpot = objective.getScore(ChatColor.RED + " ");
                                                blankSpot.setScore(18);
                                                Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                                                Rank.setScore(17);
                                                Score RankR = objective.getScore(ChatColor.WHITE + rankeddata.getRank());
                                                RankR.setScore(16);
                                                Score blankSpot2 = objective.getScore(ChatColor.RED + "  ");
                                                blankSpot2.setScore(15);
                                                Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                                                spot.setScore(11);
                                                if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(ranked.getLocation()).getRegions().size() == 0) {
                                                    Score locResult = objective.getScore(ChatColor.WHITE + "???");
                                                    locResult.setScore(10);

                                                } else {
                                                    for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(ranked.getWorld()).getApplicableRegions(ranked.getLocation())) {
                                                        Score spotResult = objective.getScore(ChatColor.WHITE + kingdomRegion.getId().replaceAll("new-rhean", "§fNew-Rhean").replaceAll("katakinos", "§fKatakinos").replaceAll("spawn", "§fSpawn").replaceAll("ashanti", "§fAshanti").replaceAll("kayantos", "§fKayantos").replaceAll("tyros", "§fTyros").replaceAll("wellcliff", "§fWellcliff").replaceAll("peacevillage", "§fPeaceVillage").replaceAll("lumbridge", "§fLumbridge").replaceAll("zetios", "§fZetios").replaceAll("ziladia", "§fZiladia"));
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
                                                    board.getTeam(kds.get("naam").toString()).setPrefix(kds.get("prefix-color").toString().replace('&', '§'));
                                                }
                                            }

                                            for (Player p : net.zwet.publickingdom.events.ScoreBoardCreateEvent.teamHashMap.keySet()) {
                                                board.getTeam(net.zwet.publickingdom.events.ScoreBoardCreateEvent.teamHashMap.get(p).getName()).addEntry(p.getName());
                                            }

                                            ranked.setScoreboard(board);
                                            ScoreBoardCreateEvent.scoreboardMap.put(ranked, board);


                                            return true;
                                        } else {
                                            player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je kan deze rank niet geven omdat de limit is behaald!");
                                            return true;
                                        }

                                    } else {
                                        player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt niet het recht deze rank te geven!");
                                    }

                                } else {
                                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Deze rank bestaat niet!");
                                    return true;
                                }

                            } else {
                                player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt niet de juiste permissions om dit te doen!");
                                return true;
                            }
                        } else {
                            player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Deze speler zit niet in je kingdom!");
                        }
                    }
                    }
                } else {
                player.sendMessage(fireprefix + " " + ChatColor.GRAY+ "Deze speler is niet online!");
                return true;
            }
            }
        return true;
        }
    }

