package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ScoreboardToggle implements CommandExecutor {

    PublicKingdom plugin;

    public ScoreboardToggle(PublicKingdom instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Playerdata playerdata = new Playerdata(player);
            String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());

            try {
                playerdata.toggleBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage(prefix + ChatColor.GRAY + " Je hebt je scoreboard uit gezet, relog om resultaat te zien!");

        }

        return true;
    }
}
