package net.zwet.publickingdom;

import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import net.zwet.publickingdom.events.*;
import net.zwet.publickingdom.tabcompletions.CommandCompletion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

public final class PublicKingdom extends JavaPlugin {

    public String kingdomloos = this.getConfig().getString("Kingdomloos");
    @Override
    public void onEnable() {
        PluginCommand baseCommand = getCommand("K");
        baseCommand.setExecutor(new net.zwet.publickingdom.commands.BaseCommand(this));
        baseCommand.setTabCompleter(new CommandCompletion());
        registerEvents(this, new BB(this), new BPEvent(this), new ChatEvent(this), new ChestOpenEvent(this), new Respawn(this), new HitEvent(this)
                , new JoinEvent(this), new MoveEvent(this), new ScoreBoardCreateEvent(this), new net.zwet.publickingdom.events.InviteInvClick(this)
                , new net.zwet.publickingdom.events.AllyHit(this), new net.zwet.publickingdom.events.TPJoin(this), new net.zwet.publickingdom.events.DeathEvent(this), new net.zwet.publickingdom.events.KillEvent(this)
                , new net.zwet.publickingdom.events.DeathMessage(this), new InfoInvClick(this), new KingdomloosJoin(this));
        saveDefaultConfig();


        saveDefaultTargaryen();
        getLogger().info("PublicKingdom gestart!");


            File playersdir = new File(getDataFolder() + File.separator + "players");
            if (!playersdir.exists()) {
                playersdir.mkdir();
            }
            File kingdomsdir = new File(getDataFolder() + File.separator + "kingdoms");
            if (!kingdomsdir.exists()) {
                kingdomsdir.mkdir();

        }


    }

    @Override
    public void onDisable() {

        getLogger().info("PublicKingdom gestopt!");
    }
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    private FileConfiguration TargaryenConfig = null;
    private File CTargayenConfig = null;
    public void loadTargaryenConfig(){
        if (CTargayenConfig== null){
            CTargayenConfig = new File(getDataFolder().getAbsolutePath() + File.separator + "houses", "Targayen.yml");
        }
        TargaryenConfig = YamlConfiguration.loadConfiguration(CTargayenConfig);

        Reader defTargaryenStream = null;
        try {
            defTargaryenStream = new InputStreamReader(this.getResource("Targaryen.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defTargaryenStream != null){
            YamlConfiguration defTargaryen = YamlConfiguration.loadConfiguration(defTargaryenStream);
            TargaryenConfig.setDefaults(defTargaryen);
        }
    }

    public void sendActionBarText(Player p, String message)
    {
        CraftPlayer cp = (CraftPlayer)p;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc);
        cp.getHandle().playerConnection.sendPacket(ppoc);
    }



    public void saveDefaultTargaryen(){
        if(CTargayenConfig == null){
            CTargayenConfig = new File(getDataFolder().getAbsolutePath() + File.separator + "houses", "Targaryen.yml");
        }
        if (!CTargayenConfig.exists()){
            this.saveResource("Targaryen.yml", false);
        }
    }
    public FileConfiguration getTargaryenConfig(){
        if (TargaryenConfig == null){
            loadTargaryenConfig();
        }
        return TargaryenConfig;
    }
    public void saveTargaryen(){
        if (TargaryenConfig == null || CTargayenConfig == null){
            return;
        }
        try{
            getTargaryenConfig().save(CTargayenConfig);
        }catch (IOException ex){
            getLogger().log(Level.SEVERE, "Kan Targaryen yaml niet maken!", ex);
        }
    }
}


