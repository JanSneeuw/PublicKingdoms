package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.objects.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Clean implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (player.hasPermission("publickingdom.staff")){
                    Kingdom kingdom = null;
                    try{
                        kingdom = new Kingdom(args[0]);
                    }catch (NullPointerException | NoSuchKingdomException e){
                        Bukkit.getLogger().warning(args[0] + " bestaat niet!");
                    }
                    if (kingdom != null){
                        try {
                            kingdom.clean();
                        } catch (IOException | InvalidConfigurationException e) {
                         Bukkit.getLogger().warning("Kan " + args[0] + " niet cleanen!");
                        }
                    }
                }
            }
        }
        return true;
    }
}
