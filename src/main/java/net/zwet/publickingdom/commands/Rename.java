package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.objects.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Rename implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("firekingdom.staff")){
                if (args.length == 2){
                    Kingdom kingdom = null;
                    try{
                        kingdom = new Kingdom(args[0]);
                    }catch (NullPointerException | NoSuchKingdomException e){
                        Bukkit.getLogger().warning(args[0] + " bestaat niet! {Rename: 21}");
                    }
                    if (kingdom != null){
                        if (!args[0].equalsIgnoreCase(args[1])){
                            try {
                                kingdom.rename(args[1]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
