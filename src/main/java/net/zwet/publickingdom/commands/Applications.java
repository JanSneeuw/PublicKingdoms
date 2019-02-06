package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.objects.Application;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.validation.HasPermissionValidation;
import net.zwet.publickingdom.validation.InKingdomValidation;
import net.zwet.publickingdom.validation.Validator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Applications implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Validator applicationsValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new HasPermissionValidation(player, "k.applications"));
                boolean passOn = applicationsValidator.executeValidations();
                if (passOn){
                    Kingdom kingdom = new Kingdom(player);
                    kingdom.sendApplications(player);
                }else{
                    applicationsValidator.getErrormessage();
                }

            }
        }

        return true;
    }
}
