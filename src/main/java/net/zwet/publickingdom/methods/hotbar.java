package net.zwet.publickingdom.methods;

import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class hotbar {
    static Plugin plugin = Bukkit.getPluginManager().getPlugin("PublicKingdom");
    public static void remakeHotbar(Player p)
    {
        Playerdata playerdata = new Playerdata(p);
        if (playerdata.exists()) {
            int kills = playerdata.getKills();
            int deaths = playerdata.getDeaths();

            String str = "&a&lKills:&7&l " + (playerdata.contains("kills") ? kills : 0);

            str = str + " &c&l     Deaths:&7&l " + (playerdata.contains("deaths") ? deaths : 0);

            sendActionBarText(p, ChatColor.translateAlternateColorCodes('&', str));
        }
    }


    public static void sendActionBarText(Player p, String message)
    {
        CraftPlayer cp = (CraftPlayer)p;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc);
        cp.getHandle().playerConnection.sendPacket(ppoc);
    }
}
