package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.validation.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Application implements CommandExecutor {
    public static Map<UUID, net.zwet.publickingdom.objects.Application> applicationMap = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                net.zwet.publickingdom.objects.Application application = new net.zwet.publickingdom.objects.Application(player);
                Kingdom kingdom = null;
                try {
                    kingdom = new Kingdom(args[0]);
                } catch (NoSuchKingdomException e) {
                    System.out.println(args[0] + " is not a kingdom!");
                }
                Validator solValidator = new Validator().addValidation(new NotInKingomValidation(player)).addValidation(new KingdomExistsValidation(args[0])).addValidation(new AlreadyGotApplicationValidation(player, kingdom));
                boolean passOn = solValidator.executeValidations();
                if (passOn) {
                    application.setKingdom(kingdom);
                    applicationMap.put(player.getUniqueId(), application);
                } else {
                    Bukkit.getLogger().warning(solValidator.getErrormessage());
                }
            }
        }
        return true;
    }
}
