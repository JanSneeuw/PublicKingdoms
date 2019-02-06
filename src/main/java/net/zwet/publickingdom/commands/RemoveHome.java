package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class RemoveHome implements CommandExecutor {
    PublicKingdom plugin;
    public RemoveHome(PublicKingdom instance){
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            Player player = (Player) sender;
            Validator removeValidator = new Validator().addValidation(new ArgsEqualsLengthValidation(1, args.length)).addValidation(new HasBukkitPermissionValidation(player, "firekingdom.home")).addValidation(new HasCertainHome(player, args[0]));
            boolean passOn = removeValidator.executeValidations();
            if (passOn){
                Playerdata playerdata = new Playerdata(player);
                try {
                    playerdata.removeHome(args[0]);
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt de home " + args[0] + " verwijderd!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
