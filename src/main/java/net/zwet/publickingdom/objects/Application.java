package net.zwet.publickingdom.objects;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Application {


    Plugin plugin = Bukkit.getPluginManager().getPlugin("PublicKingdom");
    private String message;
    private Kingdom kingdom;
    private File kingdomFile;
    private FileConfiguration kingdomFileConfiguration;
    private Player player;

    public Application(){
    }

    public Application(String Application, Kingdom kingdom, Player player){
        this.message = Application;
        this.kingdom = kingdom;
        this.player = player;
        this.kingdomFile = new File(plugin.getDataFolder() + File.separator + "applications" + File.separator + kingdom.getName() + ".yml");
        this.kingdomFileConfiguration = YamlConfiguration.loadConfiguration(this.kingdomFile);
    }

    public Application(Kingdom kingdom, Player player){
        this.kingdom = kingdom;
        this.player = player;
        this.kingdomFile = new File(plugin.getDataFolder() + File.separator + "applications" + File.separator + kingdom.getName() + ".yml");
        this.kingdomFileConfiguration = YamlConfiguration.loadConfiguration(this.kingdomFile);
    }

    public void send() throws IOException {
        this.kingdomFileConfiguration.set(this.player.getUniqueId().toString(), this.message);
        this.kingdomFileConfiguration.save(this.kingdomFile);
    }

    public String getMessage(){
        return this.kingdomFileConfiguration.getString(this.player.getUniqueId().toString());
    }

    public Application setMessage(String application){
        this.message = application;
        return this;
    }
    public Application fromPlayer(Player player){
        this.player = player;
        return this;
    }
}
