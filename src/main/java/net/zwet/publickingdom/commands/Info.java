package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class Info implements CommandExecutor {
    PublicKingdom plugin;

    public Info(PublicKingdom instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Playerdata Pdata = new Playerdata(player);

        if (args.length == 1) {
            File kingdomdatafile = new File(plugin.getDataFolder() + File.separator + "kingdoms" + File.separator + args[0] + ".yml");
            File folder = new File(plugin.getDataFolder() + File.separator + "players");
            File[] files = folder.listFiles();
            YamlConfiguration hs = new YamlConfiguration();
            String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
            //noinspection deprecation
            if (Bukkit.getPlayer(args[0]) != null) {
                @SuppressWarnings("deprecation")
                Player hp = Bukkit.getPlayer(args[0]);
                Playerdata infodata = new Playerdata(hp);
                Inventory playerinventory = Bukkit.createInventory(null, 27, ChatColor.GRAY + "INFO");
                playerinventory.setItem(11, infodata.getKillsItem());
                playerinventory.setItem(12, infodata.getKingdomItem());
                playerinventory.setItem(13, infodata.getNameItem());
                playerinventory.setItem(14, infodata.getRankItem());
                playerinventory.setItem(15, infodata.getDeathsItem());
                player.openInventory(playerinventory);
                    /*player.sendMessage("§8§l§m-----------------§7[ §c§lFire§e§lKingdom§7 ]§8§l§m----------------");
                    player.sendMessage("§7Naam§f: " + infodata.getName());
                    player.sendMessage("§7Kingdom§f: " + ChatColor.WHITE + infodata.getKingdom().getColoredName());
                    player.sendMessage("§7Rank§f: " + ChatColor.WHITE + infodata.getRank());
                    player.sendMessage("§8§l§m--------------------------------------------");*/
                return true;
            } else{
            Kingdom kingdom = null;
            try {
                kingdom = new Kingdom(args[0]);
            } catch (NoSuchKingdomException  | NullPointerException e) {
                Bukkit.getLogger().warning(args[0] + " bestaat niet! {info: 62}");
            }
            if (kingdom != null) {
                Inventory kingdominventory = Bukkit.createInventory(null, 27, kingdom.getColoredName() + " "+ ChatColor.GRAY + "INFO");
                kingdominventory.setItem(10, kingdom.getKillsItem());
                kingdominventory.setItem(11, kingdom.getKingItem());
                kingdominventory.setItem(12, kingdom.getAlliesItem());
                kingdominventory.setItem(13, kingdom.getNameItem());
                kingdominventory.setItem(14, kingdom.getEnemiesItem());
                kingdominventory.setItem(15, kingdom.getPlayersItem());
                kingdominventory.setItem(16, kingdom.getDeathsItem());
                player.openInventory(kingdominventory);
                /*player.sendMessage("§8§l§m-----------------§7[ §c§lFire§e§lKingdom§7 ]§8§l§m----------------");
                player.sendMessage("§7Kingdom§f: §7[" + kingdom.getColoredName() + "§7]");
                player.sendMessage("§7Bondgenoten§f: §f" + kingdom.getAlliesString());
                player.sendMessage("§7Vijanden§f: §f" + kingdom.getEnemiesString());
                player.sendMessage("§7Kingdom leden§f: §f" + kingdom.getPlayersString());
                player.sendMessage("§8§l§m--------------------------------------------");*/
            }

        }
        }else if (args.length == 0) {
            if (Pdata.isInKingdom()) {
                Kingdom kingdom = new Kingdom(player);
                if (kingdom.exists()) {
                    Inventory kingdominventory = Bukkit.createInventory(null, 27, kingdom.getColoredName() + " " + ChatColor.GRAY + "INFO");
                    kingdominventory.setItem(10, kingdom.getKillsItem());
                    kingdominventory.setItem(11, kingdom.getKingItem());
                    kingdominventory.setItem(12, kingdom.getAlliesItem());
                    kingdominventory.setItem(13, kingdom.getNameItem());
                    kingdominventory.setItem(14, kingdom.getEnemiesItem());
                    kingdominventory.setItem(15, kingdom.getPlayersItem());
                    kingdominventory.setItem(16, kingdom.getDeathsItem());
                    player.openInventory(kingdominventory);
                }
            }
        }
        return false;
    }
}
