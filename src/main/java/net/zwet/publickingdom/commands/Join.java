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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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


public class Join implements CommandExecutor {
    PublicKingdom plugin;

    public Join(PublicKingdom instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File[] kingdomFiles = plugin.getDataFolder().listFiles();
        if (args.length == 1) {
            Player player = (Player) sender;
            String players = player.getName();
            Playerdata playerdata = new Playerdata(player);
            String kingdomArgs = args[0].trim();
            String HA = (kingdomArgs.charAt(0) + "").toUpperCase() + kingdomArgs.substring(1);
            FileConfiguration kingdomdata = null;
            File kingdomdatafile = new File(plugin.getDataFolder() + File.separator + "kingdoms" + File.separator + HA + ".yml");
            kingdomdata = YamlConfiguration.loadConfiguration(kingdomdatafile);
            Kingdom kingdom = null;
            try {
                kingdom = new Kingdom(HA);
            } catch (NoSuchKingdomException e) {
                Bukkit.getLogger().warning(HA + " bestaat niet! {Join: 50}");
            }
            Scoreboard board = ScoreBoardCreateEvent.manager.getNewScoreboard();
            if (Invite.invites.contains(players + " " + HA)) {
                if (playerdata.isInKingdom()) {
                    if (kingdom.exists()) {
                        File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
                        YamlConfiguration kds = new YamlConfiguration();
                        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

                        try {
                            playerdata.setKingdom(kingdom);
                            playerdata.setRank(kingdom.getDefaultRank());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (playerdata.boardIsOn()) {
                            Objective objective = board.registerNewObjective("PublicKingdom", "dummy");
                            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Scoreboard-Title")));

                            Score kingdomS = objective.getScore(ChatColor.RED + "§cKingdom:");
                            kingdomS.setScore(20);
                            Score kingdomR = objective.getScore(ChatColor.WHITE + playerdata.getKingdomName());
                            kingdomR.setScore(19);
                            Score blankSpot = objective.getScore(ChatColor.GRAY + " ");
                            blankSpot.setScore(18);
                            Score Rank = objective.getScore(ChatColor.RED + "§cRank:");
                            Rank.setScore(17);
                            Score RankR = objective.getScore(ChatColor.WHITE + playerdata.getRank());
                            RankR.setScore(16);
                            Score blankSpot2 = objective.getScore(ChatColor.GRAY + "  ");
                            blankSpot2.setScore(15);
                            Score spot = objective.getScore(ChatColor.GRAY + "§cLocatie:");
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
                                    Score spotResult = objective.getScore(ChatColor.GRAY + kingdomRegion.getId().replaceAll("new-rhean", "§fNew-Rhean").replaceAll("katakinos", "§fKatakinos").replaceAll("spawn", "§fSpawn").replaceAll("ashanti", "§fAshanti").replaceAll("kayantos", "§fKayantos").replaceAll("tyros", "§fTyros").replaceAll("wellcliff", "§fWellcliff").replaceAll("peacevillage", "§fPeaceVillage").replaceAll("lumbridge", "§fLumbridge").replaceAll("zetios", "§fZetios").replaceAll("ziladia", "§fZiladia"));
                                    spotResult.setScore(10);
                                }
                            }
                            if (kingdomdata.getString("King") != null) {
                                FileConfiguration kingdata = null;
                                File kingdatafile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + kingdomdata.getString("King") + ".yml");
                                if (kingdatafile.exists()) {
                                    kingdata = YamlConfiguration.loadConfiguration(kingdatafile);
                                    Score blankSpot3 = objective.getScore(ChatColor.YELLOW + "     ");
                                    blankSpot3.setScore(12);
                                    Score king = objective.getScore(ChatColor.GRAY + "§cKoning:");
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
                                playersescoreboard.getTeam(kingdomdata.get("naam").toString()).addEntry(players);
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
                        }
                        board.getTeam(HA).addEntry(player.getName());
                        ScoreBoardCreateEvent.teamHashMap.put(player, board.getTeam(HA));

                        player.setScoreboard(board);
                        ScoreBoardCreateEvent.scoreboardMap.put(player, board);

                        Invite.invites.remove(player + " " + HA);
                        player.sendMessage(prefix + " " + ChatColor.GRAY + "Je bent kingdom§f " + HA + " §7gejoined!");

                        World kdworld = Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"));
                        Location spawnloc = new Location(kdworld, kingdomdata.getInt("spawn.X"), kingdomdata.getInt("spawn.Y"), kingdomdata.getInt("spawn.Z"));
                        player.teleport(spawnloc);
                        return true;
                    } else {
                        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                        player.sendMessage(prefix + " " + ChatColor.GRAY + "Dit kingdom bestaat niet!");
                    }
                } else {
                    String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                    player.sendMessage(prefix + " " + ChatColor.GRAY + "Je zit al in een kingdom!");
                }


            } else {
                String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                sender.sendMessage(prefix + " " + ChatColor.GRAY + "Je bent niet geinvite voor dit kingdom!");
            }
        } else {
            String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            sender.sendMessage(prefix + " " + ChatColor.GRAY + "Command verkeerd gebruikt, gebruik /k join (kingdom)!");
        }
        return true;
    }
}
