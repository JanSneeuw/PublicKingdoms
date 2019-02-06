package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ally implements CommandExecutor {
    PublicKingdom plugin;

    public Ally(PublicKingdom instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        Kingdom kingdom = new Kingdom(player);
        Playerdata PlayerData = new Playerdata(player);
        if (args.length > 0) {
            if (!args[0].equalsIgnoreCase("remove")) {
                Kingdom allykingdom = null;
                try {
                    allykingdom = new Kingdom(args[0]);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    Bukkit.getLogger().warning(player.getName() + " probeerde " + args[0] + " ally te maken maar deze bestaat niet!");
                }
                Validator allyValidator = new Validator().addValidation(new HasPermissionValidation(player).permission("k.relation")).addValidation(new NotAllyValidation(kingdom, allykingdom))
                        .addValidation(new NotEnemyValidation(kingdom, allykingdom)).addValidation(new MaxAlliesReachedValidation(kingdom));
                boolean passOn = allyValidator.executeValidations();
                    if (passOn) {
                        kingdom.addAlly(allykingdom);
                            for (Player allplayers : Bukkit.getOnlinePlayers()) {
                                allplayers.sendMessage(fireprefix + ChatColor.WHITE + kingdom.getName() + " §7en§f " + allykingdom.getName() + "§7 hebben nu een alliantie!");
                            }
                    }else{
                        player.sendMessage(fireprefix + ChatColor.GRAY + " " + allyValidator.getErrormessage());
                    }
            }else {
                Kingdom allykingdom = null;
                try {
                    allykingdom = new Kingdom(args[1]);
                } catch (NoSuchKingdomException e) {
                    e.printStackTrace();
                }
                Validator allyValidator = new Validator().addValidation(new AreAlliesValidation(kingdom, allykingdom)).addValidation(new HasPermissionValidation(player).permission("k.relation"));
                boolean passOn = allyValidator.executeValidations();
                if (passOn) {
                    kingdom.removeAlly(allykingdom);
                    for (Player allplayers : Bukkit.getOnlinePlayers()) {
                        allplayers.sendMessage(fireprefix + ChatColor.WHITE + kingdom.getName() + " §7en§f " + allykingdom.getName() + "§7 zijn niet langer bondgenoten!");
                    }
                } else {
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + allyValidator.getErrormessage());
                }
            }
        }

        return true;
    }
}
