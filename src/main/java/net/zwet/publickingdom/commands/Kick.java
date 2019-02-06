package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.events.ScoreBoardCreateEvent;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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

import static net.zwet.publickingdom.events.ScoreBoardCreateEvent.manager;

public class Kick implements CommandExecutor {
    PublicKingdom plugin;

    public Kick(PublicKingdom instance) {
        plugin = instance;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            Player player = (Player) sender;
            Playerdata playerdata = new Playerdata(player);
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            Validator onlineValidator = new Validator().addValidation(new PlayerOnlineValidation(args[0]));
            boolean passOnOnline = onlineValidator.executeValidations();
            if (passOnOnline) {
                //noinspection deprecation
                Validator kickValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new InKingdomValidation(Bukkit.getPlayer(args[0]))).addValidation(new ShareKingdomValidation(player, Bukkit.getPlayer(args[0])))
                        .addValidation(new HasPermissionValidation(player, "k.kick")).addValidation(new IsHigherValidation(player).rank(new Playerdata(Bukkit.getPlayer(args[0])).getRank()));
                boolean kickPasson = kickValidator.executeValidations();
                if (kickPasson || player.hasPermission("firekingdom.staff")) {
                    //noinspection deprecation
                    Player kicked = Bukkit.getPlayer(args[0]);
                    Playerdata kickeddata = new Playerdata(kicked);
                    try {
                        kickeddata.setKingdom(plugin.getConfig().get("Kingdomloos").toString());
                        kickeddata.setRank(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File[] kdfiles = new File(plugin.getDataFolder() + File.separator + "kingdoms").listFiles();
                    YamlConfiguration kds = new YamlConfiguration();

                    Scoreboard board = manager.getNewScoreboard();
                    if (kickeddata.boardIsOn()) {
                        Objective objective = board.registerNewObjective("FireKingdom", "dummy");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName(ChatColor.WHITE + "   " + ChatColor.RED + ChatColor.BOLD + "Fire" + ChatColor.YELLOW + ChatColor.BOLD + "Kingdom" + ChatColor.RESET + ChatColor.WHITE + "   ");
                        Score k = objective.getScore(ChatColor.GRAY + "§cKingdom:");
                        k.setScore(20);
                        Score kingdomR = objective.getScore(ChatColor.WHITE + "GEEN ");
                        kingdomR.setScore(19);
                        Score blankSpot = objective.getScore(ChatColor.GRAY + "  ");
                        blankSpot.setScore(18);
                        Score Rank = objective.getScore(ChatColor.GRAY + "§cRank:");
                        Rank.setScore(17);
                        Score RankR = objective.getScore(ChatColor.WHITE + "GEEN");
                        RankR.setScore(16);
                    }

                    ScoreBoardCreateEvent.teamHashMap.remove(kicked);
                    if (kdfiles != null) {
                        for (File kingdoms : kdfiles) {
                            try {
                                kds.load(kingdoms);
                            } catch (IOException | InvalidConfigurationException e) {
                                Bukkit.getLogger().warning("Kan kingdom lijst niet laden!");
                            }
                            board.registerNewTeam(kds.get("naam").toString());
                            board.getTeam(kds.get("naam").toString()).setAllowFriendlyFire(false);
                            board.getTeam(kds.get("naam").toString()).setPrefix(kds.get("prefix-color").toString().replace('&', '§'));
                        }

                    }
                    for (Player p : ScoreBoardCreateEvent.teamHashMap.keySet()) {
                        board.getTeam(ScoreBoardCreateEvent.teamHashMap.get(p).getName()).addEntry(p.getName());
                    }
                    for (Player cpa : Bukkit.getOnlinePlayers()) {
                        if (!cpa.getName().equals(kicked.getName())) {
                            Scoreboard scoreboard = cpa.getScoreboard();
                            scoreboard.getTeam(playerdata.getKingdomName()).removeEntry(kicked.getName());

                        }
                    }

                    kicked.setScoreboard(board);
                    ScoreBoardCreateEvent.scoreboardMap.put(player, board);
                    double x = -79.527; //This is a coordinate I tried with
                    double y = 34; //This is a coordinate I tried with
                    double z = 236.518; //This is a coordinate I tried with
                    World w = Bukkit.getWorld(plugin.getConfig().get("kingdomloos-spawn").toString());

                    Location l = new Location(w, x, y, z, -92.6F, -0.3F); //Try location
                    kicked.teleport(l);

                    player.sendMessage(fireprefix + " " + ChatColor.WHITE + kicked.getName() + " §7is gekicked van je kingdom!");
                    return true;
                }
            } else if (onlineValidator.getErrormessage().contains("is not online!")) {
                Validator offlineKickValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new InKingdomValidation(new Playerdata(args[0])))
                        .addValidation(new ShareKingdomValidation(playerdata, new Playerdata(args[0]))).addValidation(new HasPermissionValidation(player, "k.kick"))
                        .addValidation(new IsHigherValidation(player, new Playerdata(args[0]).getRank()));
                boolean pass = offlineKickValidator.executeValidations();
                if (pass || player.hasPermission("firekingdom.staff")){
                    Playerdata kickeddata = null;
                    try {
                        kickeddata = new Playerdata(args[0]);
                    } catch (NullPointerException e) {
                        Bukkit.getLogger().warning(args[0] + " heeft geen playerdata {Kick: 122}");
                    }
                    try {
                        kickeddata.setKingdom(plugin.getConfig().get("Kingdomloos").toString());
                        kickeddata.setRank(null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    player.sendMessage(fireprefix + " " + ChatColor.WHITE + kickeddata.getName() + " §7is gekicked van je kingdom!");
                } else {
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + offlineKickValidator.getErrormessage());
                }
            }
        }

        return true;
    }
}

