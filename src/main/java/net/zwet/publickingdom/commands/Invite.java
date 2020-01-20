package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Invite implements CommandExecutor {
    PublicKingdom plugin;

    public Invite(PublicKingdom instance) {
        plugin = instance;
    }

    public static ArrayList invites = new ArrayList<String>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            Player player = (Player) sender;

            Playerdata playerdata = new Playerdata(player);
            Kingdom kingdom = new Kingdom(player);
            //noinspection deprecation
            Validator inviteValidator = new Validator().addValidation(new ArgsEqualsLengthValidation(1, args.length)).addValidation(new PlayerOnlineValidation(args[0])).addValidation(new NotInKingomValidation(new Playerdata(Bukkit.getPlayer(args[0]))))
                    .addValidation(new InKingdomValidation(playerdata));
            boolean passOn = inviteValidator.executeValidations();
            if (passOn) {
                @SuppressWarnings("deprecation")
                Player invited = Bukkit.getPlayer(args[0]);
                String inviteds = invited.getName();
                FileConfiguration inviteddata = null;
                File folder = new File(plugin.getDataFolder() + File.separator + "players");
                File[] files = folder.listFiles();
                YamlConfiguration hs = new YamlConfiguration();
                File inviteddatafile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + invited.getUniqueId().toString() + ".yml");
                inviteddata = YamlConfiguration.loadConfiguration(inviteddatafile);
                String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
                String Prefix = kingdom.getPrefix().replace("&", "§");

                if (inviteddata.get("kingdom").equals(plugin.getConfig().get("Kingdomloos"))) {
                    if (playerdata.isInKingdom()) {
                        if (playerdata.hasPermission("k.invite")) {
                            int ledenaantal = kingdom.getPlayersAmount();
                            invites.add(inviteds + " " + kingdom.getName());
                            invited.sendMessage(prefix + " " + ChatColor.GRAY + "Je bent geinvite voor kingdom §f" + kingdom.getColoredName() + "§7!");
                            Inventory inventory = Bukkit.createInventory(null, 36, kingdom.getColoredName() + " §7- " + ChatColor.GRAY + "Invite");

                            ItemStack Accept = new ItemStack(Material.EMERALD_BLOCK);
                            ItemMeta AcceptMeta = Accept.getItemMeta();
                            AcceptMeta.setDisplayName(ChatColor.GREEN + "Join " + kingdom.getName());
                            Accept.setItemMeta(AcceptMeta);

                            ItemStack Deny = new ItemStack(Material.BARRIER);
                            ItemMeta DenyMeta = Deny.getItemMeta();
                            DenyMeta.setDisplayName(ChatColor.GRAY + "Weiger de invite");
                            Deny.setItemMeta(DenyMeta);

                            ItemStack info = new ItemStack(Material.PAPER);
                            ItemMeta infoMeta = info.getItemMeta();
                            infoMeta.setDisplayName(ChatColor.DARK_GRAY + "Kingdom info:");
                            if (kingdom.getKing() != null) {
                                String koningnaam = kingdom.getKing().getName();
                                infoMeta.setLore(Arrays.asList("§7Prefix: " + Prefix, "§7Koning: §f" + koningnaam, "§7Leden: §f" + ledenaantal));
                            }else{
                                infoMeta.setLore(Arrays.asList("§7Prefix: " + Prefix, "§7Koning: §fGEEN" , "§7Leden: §f" + ledenaantal));

                            }
                            info.setItemMeta(infoMeta);

                            inventory.setItem(21, Accept);
                            inventory.setItem(23, Deny);
                            inventory.setItem(13, info);
                            invited.openInventory(inventory);
                            player.sendMessage(prefix + ChatColor.GRAY + " Je hebt " + inviteds + " geinvite!");

                            return true;
                        } else {
                            sender.sendMessage(prefix + " " + ChatColor.GRAY + "Je hebt niet de juiste permissions om dit te doen!");
                        }
                    }
                } else {
                    player.sendMessage(prefix + " " + ChatColor.WHITE + invited.getName() + " §7zit al in een kingdom!");
                }
                return true;
            }
        }


        return false;


    }
}