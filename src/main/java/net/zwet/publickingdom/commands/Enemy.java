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

public class Enemy implements CommandExecutor {
    PublicKingdom plugin;
    public Enemy(PublicKingdom instance) {
        plugin = instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        Kingdom kingdom = new Kingdom(player);
        Playerdata PlayerData = new Playerdata(player);
        if (!args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1) {
                Kingdom enemykingdom = null;
                try {
                    enemykingdom = new Kingdom(args[0]);
                } catch (NoSuchKingdomException | NullPointerException e) {
                    Bukkit.getLogger().warning(player.getName() + " probeerde " + args[0] + " enemy te maken maar deze bestaat niet!");
                }
                Validator enemyValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new HasPermissionValidation(player).permission("k.relation"))
                        .addValidation(new NotEnemyValidation(kingdom, enemykingdom)).addValidation(new NotAllyValidation(kingdom, enemykingdom));
                boolean passOn = enemyValidator.executeValidations();
                if (passOn) {
                    kingdom.addEnemy(enemykingdom);
                    for (Player allplayers : Bukkit.getOnlinePlayers()) {
                        allplayers.sendMessage(fireprefix + " " + kingdom.getName() + " §7en§4 " + enemykingdom.getName() + " §7zijn nu enemy!");
                    }
                }else{
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + enemyValidator.getErrormessage());
                }
                return true;
            }
        }else{
            Kingdom enemykingdom = null;
            try {
                enemykingdom = new Kingdom(args[1]);
            } catch (NoSuchKingdomException e) {
                e.printStackTrace();
            }
            Validator enemyValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new HasPermissionValidation(player).permission("k.relation"))
                    .addValidation(new AreEnemiesValidation(kingdom, enemykingdom));
            boolean passOn = enemyValidator.executeValidations();
            if (passOn) {
                kingdom.removeEnemy(enemykingdom);
                for (Player allplayers : Bukkit.getOnlinePlayers()) {
                    allplayers.sendMessage(fireprefix + " §4" + kingdom.getName() + " §7en§4 " + enemykingdom.getName() + " §7zijn niet langer vijanden!!");
                }
            }else{
                player.sendMessage(fireprefix + " " + ChatColor.GRAY + enemyValidator.getErrormessage());
            }
        }

        return false;
    }
}
