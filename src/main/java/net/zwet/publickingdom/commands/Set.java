package net.zwet.publickingdom.commands;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
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
            String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            Validator setValidator = new Validator().addValidation(new PlayerOnlineValidation(args[0])).addValidation(new KingdomExistsValidation(args[1]))
                    .addValidation(new HasBukkitPermissionValidation(ps, "publickingdom.staff"));
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
                                Objective objective = board.registerNewObjective("PublicKingdom", "dummy");
                                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                                objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Scoreboard-Title")));

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
                                LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                                RegionQuery query = regionContainer.createQuery();
                                if (query.getApplicableRegions(lplayer.getLocation()).getRegions().size() == 0) {
                                    Score locResult = objective.getScore(ChatColor.WHITE + "???");
                                    locResult.setScore(10);

                                } else {
                                    ApplicableRegionSet set = query.getApplicableRegions(lplayer.getLocation());
                                    for (ProtectedRegion kingdomRegion : set) {
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
                                }else{
                                    Score blankSpot3 = objective.getScore(ChatColor.BLUE + "     ");
                                    blankSpot3.setScore(12);
                                    Score king = objective.getScore(ChatColor.RED + "Koning:");
                                    king.setScore(14);
                                    Score kingResult = objective.getScore(ChatColor.WHITE + "GEEN");
                                    kingResult.setScore(13);
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
                                    if (!kds.getString("team-prefix").equalsIgnoreCase("NONE")) {
                                        board.getTeam(kds.get("naam").toString()).setPrefix(ChatColor.translateAlternateColorCodes('&',kds.getString("team-prefix")));
                                    }
                                    if (!kds.getString("name-color").equalsIgnoreCase("NONE")) {
                                        board.getTeam(kds.get("naam").toString()).setColor(ChatColor.getByChar(kds.getString("name-color").replace('&', '§')));
                                    }
                                }
                            }
                            board.getTeam(args[1]).addEntry(player.getName());
                            ScoreBoardCreateEvent.teamHashMap.put(player, board.getTeam(args[1]));

                            player.setScoreboard(board);
                            ScoreBoardCreateEvent.scoreboardMap.put(player, board);

                            sender.sendMessage(prefix + " " + ChatColor.GRAY + "Je hebt§f " + players + " §7in §f" + kingdom.getName() + "§7 geplaatst!");
                            return true;

            } else {
                sender.sendMessage(prefix + " " + ChatColor.GRAY + setValidator.getErrormessage());
            }
        }
        return true;
    }
}



