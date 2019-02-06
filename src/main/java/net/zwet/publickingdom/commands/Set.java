package net.zwet.publickingdom.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.events.ScoreBoardCreateEvent;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;

public class Set implements CommandExecutor {
    PublicKingdom plugin;

    public Set(PublicKingdom instance) {
        plugin = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player ps = (Player) sender;

        if (args.length == 2) {
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            Validator setValidator = new Validator().addValidation(new PlayerOnlineValidation(args[0])).addValidation(new KingdomExistsValidation(args[1]))
                    .addValidation(new HasBukkitPermissionValidation(ps, "firekingdom.staff"));
            boolean passOn = setValidator.executeValidations();
            if (passOn) {
                @SuppressWarnings("deprecation")
                Player player = Bukkit.getPlayer(args[0]);
                String players = player.getName();

                Scoreboard board = net.zwet.publickingdom.events.ScoreBoardCreateEvent.manager.getNewScoreboard();
                Playerdata playerdata = new Playerdata(player);
                Kingdom kingdom = null;
                    try {
                        kingdom = new Kingdom(args[1]);
                    } catch (NoSuchKingdomException | NullPointerException e) {
                        Bukkit.getLogger().warning(args[1] + " bestaat niet {Set: 57}");
                    }
                    File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
                    YamlConfiguration kds = new YamlConfiguration();

                    try {
                                playerdata.setKingdom(kingdom.getName());
                                playerdata.setRank(kingdom.getDefaultRank());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (playerdata.boardIsOn()) {
                                Objective objective = board.registerNewObjective("FireKingdom", "dummy");
                                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                                objective.setDisplayName(ChatColor.WHITE + "   " + ChatColor.RED + ChatColor.BOLD + "Fire" + ChatColor.YELLOW + ChatColor.BOLD + "Kingdom" + ChatColor.RESET + ChatColor.WHITE + "   ");

                                Score k = objective.getScore(ChatColor.RED + "Kingdom:");
                                k.setScore(20);
                                Score kingdomR = objective.getScore(ChatColor.WHITE + kingdom.getName());
                                kingdomR.setScore(19);
                                Score blankSpot = objective.getScore(ChatColor.GRAY + " ");
                                blankSpot.setScore(18);
                                Score Rank = objective.getScore(ChatColor.RED + "Rank:");
                                Rank.setScore(17);
                                Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                                RankR.setScore(16);
                                Score blankSpot2 = objective.getScore(ChatColor.RED + "  ");
                                blankSpot2.setScore(15);
                                Score spot = objective.getScore(ChatColor.RED + "Locatie:");
                                spot.setScore(11);
                                if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).getRegions().size() == 0) {
                                    Score locResult = objective.getScore(ChatColor.WHITE + "???");
                                    locResult.setScore(10);

                                } else {
                                    for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
                                        Score spotResult = objective.getScore(ChatColor.WHITE + kingdomRegion.getId().replaceAll("new-rhean", "§fNew-Rhean").replaceAll("katakinos", "§fKatakinos").replaceAll("spawn", "§fSpawn").replaceAll("ashanti", "§fAshanti").replaceAll("kayantos", "§fKayantos").replaceAll("tyros", "§fTyros").replaceAll("wellcliff", "§fWellcliff").replaceAll("peacevillage", "§fPeaceVillage").replaceAll("lumbridge", "§fLumbridge").replaceAll("zetios", "§fZetios").replaceAll("ziladia", "§fZiladia"));
                                        spotResult.setScore(10);
                                    }
                                }
                                if (kingdom.getKing() != null) {
                                    FileConfiguration kingdata = null;
                                    File kingdatafile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + kingdom.getKing() + ".yml");
                                    if (kingdatafile.exists()) {
                                        kingdata = YamlConfiguration.loadConfiguration(kingdatafile);
                                        Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                        blankSpot3.setScore(12);
                                        Score king = objective.getScore(ChatColor.RED + "Koning:");
                                        king.setScore(14);
                                        Score kingResult = objective.getScore(ChatColor.WHITE + kingdata.getString("naam"));
                                        kingResult.setScore(13);
                                    }
                                }
                            }

                            for (Player playerse : Bukkit.getOnlinePlayers()) {
                                if (!playerse.getName().equals(player.getName())) {
                                    Scoreboard playersescoreboard = playerse.getScoreboard();
                                    playersescoreboard.getTeam(args[1]).addEntry(players);
                                }
                            }
                            for (Player p : ScoreBoardCreateEvent.teamHashMap.keySet()) {
                                board.getTeam(ScoreBoardCreateEvent.teamHashMap.get(p).getName()).addEntry(p.getName());
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
                            board.getTeam(args[1]).addEntry(player.getName());
                            ScoreBoardCreateEvent.teamHashMap.put(player, board.getTeam(args[1]));

                            player.setScoreboard(board);
                            ScoreBoardCreateEvent.scoreboardMap.put(player, board);

                            sender.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt§f " + players + " §7in §f" + kingdom.getName() + "§7 geplaatst!");
                            return true;

            } else {
                sender.sendMessage(fireprefix + " " + ChatColor.GRAY + setValidator.getErrormessage());
            }
        }
        return true;
    }
}



