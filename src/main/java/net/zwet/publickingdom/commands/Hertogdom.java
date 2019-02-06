package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Hertogdom implements CommandExecutor {
    PublicKingdom plugin;

    public Hertogdom(PublicKingdom instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1){
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Playerdata playerdata = new Playerdata(player);
                Validator hertogdomValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new HasHertogdomValidation(new Kingdom(player)))
                        .addValidation(new HertogdomExistsValidation(new Kingdom(player)).hertogdom(args[0])).addValidation(new NotSpawningValidation(player));
                boolean passOn = hertogdomValidator.executeValidations();
                if (passOn) {
                    Kingdom kingdom = new Kingdom(player);
                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je wordt over§f 5 seconden §7naar je kingdom spawn geteleport!");
                    BukkitTask runnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (Spawn.hspawner.containsKey(player)) {
                                    player.teleport(kingdom.getHertogdomLocation(args[0]));
                                    Spawn.hspawner.remove(player);
                                    player.sendMessage(fireprefix + " " + ChatColor.GRAY + "Je bent nu bij je hertogdom!");
                                }
                            }
                        }.runTaskLater(plugin, 100L);
                        Spawn.hspawner.put(player, runnable);
                    }else{
                        player.sendMessage(fireprefix + " " + ChatColor.GRAY + hertogdomValidator.getErrormessage());
                    }
                }
            }
        return true;
    }
}
