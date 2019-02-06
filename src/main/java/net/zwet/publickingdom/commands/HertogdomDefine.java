package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.HasBukkitPermissionValidation;
import net.zwet.publickingdom.validation.KingdomExistsValidation;
import net.zwet.publickingdom.validation.Validator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HertogdomDefine implements CommandExecutor {
    PublicKingdom plugin;

    public HertogdomDefine(PublicKingdom instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            Validator hertogdomDefineValidator = new Validator().addValidation(new HasBukkitPermissionValidation(player, "firekingdom.staff"))
                    .addValidation(new KingdomExistsValidation(args[0]));
            boolean passOn = hertogdomDefineValidator.executeValidations();
            if (passOn) {
                if (args.length == 2) {
                    if (player.hasPermission("FireKingdom.staff")) {
                        Playerdata PlayerData = new Playerdata(player);
                        Kingdom kingdom = null;
                        try {
                            kingdom = new Kingdom(args[0]);
                        } catch (NoSuchKingdomException | NullPointerException e) {
                            Bukkit.getLogger().warning(args[0] + " bestaat niet! HertogdomDefine{40}");
                        }
                        if (kingdom != null) {
                            if (kingdom.hertogdomExists(args[1])) {
                                kingdom.defineHertogdom(args[1], player);
                            }
                        }
                    } else {
                        player.sendMessage(fireprefix + " " + ChatColor.RED + "Je staat niet in een worldguard region!");
                    }
                } else if (args.length == 3) {
                    Playerdata playerdata = new Playerdata(player);
                    Kingdom kingdom = null;
                    try {
                        kingdom = new Kingdom(args[0]);
                    } catch (NoSuchKingdomException | NullPointerException e) {
                        Bukkit.getLogger().warning(args[0] + " bestaat niet! HertogdomDefine{54}");
                    }
                    if (player.hasPermission("FireKingdom.staff")) {
                        if (kingdom != null) {
                            if (kingdom.hertogdomExists(args[1])) {
                                kingdom.defineHertogdom(args[1], args[2]);
                            }
                        }
                    }
                }
            }else{
                player.sendMessage(fireprefix + " " + ChatColor.GRAY + hertogdomDefineValidator.getErrormessage());
            }
        }
        return true;
    }
}
