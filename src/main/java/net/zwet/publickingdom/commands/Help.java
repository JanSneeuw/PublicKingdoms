package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help implements CommandExecutor {
    PublicKingdom plugin;
    public Help(PublicKingdom instance) {
            plugin = instance;
        }

        public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args){
            Player player = (Player) sender;

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("Help-Board-Header")));
            sender.sendMessage(ChatColor.GRAY + "/k ally (kingdom) = Maak een kingdom ally");
            sender.sendMessage(ChatColor.GRAY + "/k enemy (kingdom) = Maak een kingdom enemy");
            sender.sendMessage(ChatColor.GRAY + "/k info (kingdom/speler) = Krijg info van een speler of kingdom");
            sender.sendMessage(ChatColor.GRAY + "/k spawn = Ga naar je kingdom spawn");
            sender.sendMessage(ChatColor.GRAY + "/k invite (speler) = Invite een speler voor je kingdom");
            sender.sendMessage(ChatColor.GRAY + "/k join (kingdom) = Join een kingdom als je een invite hebt");
            sender.sendMessage(ChatColor.GRAY + "/k kick (speler) = Kick een speler uit je kingdom");
            sender.sendMessage(ChatColor.GRAY + "/k list = Krijg een lijst van alle kingdoms");
            sender.sendMessage(ChatColor.GRAY + "/k setspawn = Zet je kingdom spawn op huidige locatie");
            sender.sendMessage(ChatColor.GRAY + "/k setrank (speler) (rank) = Geef een speler een rank");
            sender.sendMessage(ChatColor.GRAY + "/k toggleboard = toggled het scoreboard");
            sender.sendMessage(ChatColor.GRAY + "/k hertogdom = Ga naar je hertogdom mits je deze hebt!");
            sender.sendMessage(ChatColor.GRAY + "/k sethertogdomspawn = Zet de spawn van je hertogdom mits je deze hebt!");
            if (player.hasPermission("publickingdom.home")){
                sender.sendMessage(ChatColor.GRAY + "/k sethome <naam< = zet een home (kan alleen in je eigen border!)");
                sender.sendMessage(ChatColor.GRAY + "/k home = Ga naar je geplaatste home!");
                sender.sendMessage(ChatColor.GRAY + "/k removehome <naam> = Verwijder de home <naam>!");
                sender.sendMessage(ChatColor.GRAY + "/k listhomes = Lijst van huidige homes!");
            }
            if (player.hasPermission("publickingdom.staff")) {
                sender.sendMessage(ChatColor.GRAY + "/k set (speler) (kingdom) = Zet een speler in een kingdom");
                sender.sendMessage(ChatColor.GRAY + "/k define (kingdom) = Stel de region van een kingdom in, LET OP: Je moet in de region staan!");
                sender.sendMessage(ChatColor.GRAY + "/k clean = leeg een heel kingdom!");
                sender.sendMessage(ChatColor.GRAY + "/k hertogdomdefine (kingdom) = Stel de region van het hertogdom <kingdom> in!");
                sender.sendMessage(ChatColor.GRAY + "/k rename (Kingdom) (Nieuwe kingdom naam)");
            }
            sender.sendMessage(ChatColor.GOLD + "This Plugin was made by JanSneeuw");
            sender.sendMessage("§8§l§m--------------------------------------------");
            return true;
        }
    }
