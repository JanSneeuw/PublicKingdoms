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

    public Application(Player player){
        this.player = player;
    }

    public Application(String Application, Kingdom kingdom, Player player){
        this.message = Application;
        this.kingdom = kingdom;
        this.player = player;
        setFiles();
    }

    public Application(Kingdom kingdom, Player player){
        this.kingdom = kingdom;
        this.player = player;
        setFiles();
    }

    public void setFiles(){
        this.kingdomFile = new File(plugin.getDataFolder() + File.separator + "applications" + File.separator + this.kingdom.getName() + ".yml");
        this.kingdomFileConfiguration = YamlConfiguration.loadConfiguration(this.kingdomFile);

    }

    public Application setKingdom(Kingdom kingdom){
        this.kingdom = kingdom;
        setFiles();
        return this;
    }

    public Kingdom getKingdom(){
        return this.kingdom;
    }

    public Application addText(String text){
        this.message = this.message + " " + text;
        return this;
    }

    public Application setText(String text){
        this.message = text;
        return this;
    }

    public String getText(){
        return this.message;
    }

    public void clearText(){
        this.message = null;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void send() throws IOException {
        this.kingdomFileConfiguration.set(this.player.getUniqueId().toString(), this.message);
        this.kingdomFileConfiguration.save(this.kingdomFile);
    }

    public Application fromPlayer(Player player){
        this.player = player;
        return this;
    }
}
