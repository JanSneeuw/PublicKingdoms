package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.HasBukkitPermissionValidation;
import net.zwet.publickingdom.validation.HasHomeValidation;
import net.zwet.publickingdom.validation.Validator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListHomes implements CommandExecutor {
    PublicKingdom plugin;
    public ListHomes(PublicKingdom instance){
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            Validator validator = new Validator().addValidation(new HasBukkitPermissionValidation(player, "firekingdom.home")).addValidation(new HasHomeValidation(player));
            boolean passOn = validator.executeValidations();
            if (passOn){
                Playerdata playerdata = new Playerdata(player);
                StringBuilder builder = new StringBuilder();
                for (String homes : playerdata.getHomes()){
                    builder.append(homes + ", ");
                }
                String list = builder.toString();
                list = list.substring(0, list.length() - 2);
                player.sendMessage(fireprefix + " " + ChatColor.GRAY + list);
            }else if (validator.getErrormessage().contains("home")){
                player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je hebt op het moment geen homes!");
            }
        }
        return true;
    }
}
