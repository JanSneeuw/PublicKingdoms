package net.zwet.publickingdom.objects;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Playerdata {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PublicKingdom");
    FileConfiguration playerdata = null;
    File playerdatafile = null;

    public Playerdata(Player p){
        playerdatafile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + p.getUniqueId().toString() + ".yml");
        if (playerdatafile.exists()) {
                playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
            } else {
                try {
                    playerdatafile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
    public Playerdata(UUID uuid){
        playerdatafile = new File(plugin.getDataFolder() + File.separator + "players" + File.separator + uuid.toString() + ".yml");
        if (playerdatafile.exists()){
            playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
        }

    }
    public Playerdata(String playername) throws NullPointerException{
        File[] playerfiles = new File(plugin.getDataFolder() + File.separator + "players").listFiles();
        YamlConfiguration playerfile = new YamlConfiguration();
        int count = 0;
        for (File files : playerfiles){
            try {
                playerfile.load(files);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            if (playerfile.getString("naam").equalsIgnoreCase(playername)){
                count += 1;
                playerdatafile = files;
                playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
            }
        }
        if (count == 0){
            throw new NullPointerException();
        }
    }
    public Playerdata(File file){
        playerdatafile = file;
        if (playerdatafile.exists()){
            playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
        }
    }
    public String getName(){
        return playerdata.getString("naam");
    }
    public ItemStack getNameItem(){
        List<String> namelore = new ArrayList<>();
        ItemStack name = new ItemStack(Material.NAME_TAG);
        ItemMeta namemeta = name.getItemMeta();
        namemeta.setDisplayName(ChatColor.GRAY + "Naam: ");
        namelore.add(ChatColor.BLUE + getName());
        namemeta.setLore(namelore);
        name.setItemMeta(namemeta);
        return name;
    }
    public String getKingdomName(){
        return playerdata.getString("kingdom");
    }

    public ItemStack getKingdomItem(){
        List<String> kingdomlore = new ArrayList<>();
        ItemStack kingdom = new ItemStack(Material.GOLD_SWORD);
        ItemMeta kingdommeta = kingdom.getItemMeta();
        kingdommeta.setDisplayName(ChatColor.GRAY + "Kingdom: ");
        if (isInKingdom()) {
            kingdomlore.add(ChatColor.BLUE + getKingdomName());
            kingdommeta.setLore(kingdomlore);
        }else{
            kingdomlore.add(ChatColor.BLUE + "GEEN");
            kingdommeta.setLore(kingdomlore);
        }
        kingdom.setItemMeta(kingdommeta);
        return kingdom;
    }
    public boolean isInKingdom(){
        if (!getKingdomName().equalsIgnoreCase(plugin.getConfig().getString("Kingdomloos"))){
            return true;
        }else{
            return false;
        }
    }
    public String getRank(){
        return playerdata.getString("rank");
    }
    public ItemStack getRankItem(){
        List<String> ranklore = new ArrayList<>();
        ItemStack rank = new ItemStack(Material.WORKBENCH);
        ItemMeta rankmeta = rank.getItemMeta();
        rankmeta.setDisplayName(ChatColor.GRAY + "Rank: ");
        if (isInKingdom()){
            ranklore.add(ChatColor.BLUE + getRank());
            rankmeta.setLore(ranklore);
        }else{
            ranklore.add(ChatColor.BLUE + "GEEN");
            rankmeta.setLore(ranklore);
        }
        rank.setItemMeta(rankmeta);
        return rank;
    }
    public boolean hasPermission(String permission){
        if (getKingdom().getRankPermissions(getRank()).contains(permission)){
            return true;
        }else{
            return false;
        }
    }
    public Kingdom getKingdom(){
        Kingdom k = null;
        try {
            k = new Kingdom(playerdata.getString("kingdom"));
        } catch (NoSuchKingdomException e) {
            e.printStackTrace();
        }
        return k;
    }
    public void setKingdom(String kingdom) throws IOException {
        playerdata.set("kingdom", kingdom);
        save();
    }
    public void setKingdom(Kingdom kingdom) throws IOException {
        playerdata.set("kingdom", kingdom.getName());
        save();
    }
    public void setRank(String rank) throws IOException {
        playerdata.set("rank", rank);
        save();
    }
    public void save() throws IOException {
        playerdata.save(playerdatafile);
    }
    public boolean boardIsOn(){
        if (playerdata.getString("scoreboard").equalsIgnoreCase("ON")) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isHigherThan(String rank) {
        FileConfiguration kingdomdata = null;
        File kingdomfile = new File(plugin.getDataFolder() + File.separator+ "kingdoms" + File.separator + getKingdomName() + ".yml");
        kingdomdata = YamlConfiguration.loadConfiguration(kingdomfile);
        if (kingdomdata.getStringList("ranks." + getRank() + ".children").contains(rank)){
            return true;
        }else{
            return false;
        }
    }
    public void toggleBoard() throws IOException {
        if (playerdata.getString("scoreboard").equalsIgnoreCase("ON")){
            playerdata.set("scoreboard", "OFF");
        }else{
            playerdata.set("scoreboard", "ON");
        }
        save();
    }
    public boolean exists(){
        if (playerdatafile.exists()){
            return true;
        }else{
            return false;
        }
    }
    public int getDeaths(){
        return playerdata.getInt("deaths");
    }
    public int getKills(){
        return playerdata.getInt("kills");
    }
    public void addDeath() throws IOException {
        if (playerdata.get("deaths") == null){
            playerdata.set("deaths", 1);
        }else {
            playerdata.set("deaths", getDeaths() + 1);
        }
        save();
    }
    public void addKill() throws IOException {
        if (playerdata.get("kills") == null){
            playerdata.set("kills", 1);
        }else {
            playerdata.set("kills", getKills() + 1);
        }
        save();
    }
    public boolean contains(String object){
        if (playerdata.get(object) != null){
            return true;
        }else{
            return false;
        }
    }
    public void setHome(Player p, String name) throws IOException {
        playerdata.set("homes." + name + ".X", p.getLocation().getX());
        playerdata.set("homes." + name + ".Y", p.getLocation().getY());
        playerdata.set("homes." + name + ".Z", p.getLocation().getZ());
        playerdata.set("homes." + name + ".WORLD", p.getLocation().getWorld().getName());
        save();
    }
    public void setHome(double x, double z, double y, String world, String name) throws IOException {

        playerdata.set("homes." + name + ".X", x);
        playerdata.set("homes." + name + ".Y", x);
        playerdata.set("homes." + name + ".Z", z);
        playerdata.set("homes." + name + ".WORLD", world);
        save();
    }
    public Location getHome(String name){
        return new Location(Bukkit.getWorld(playerdata.getString("homes." + name + ".WORLD")), playerdata.getDouble("homes." + name+ ".X"), playerdata.getDouble("homes." + name + ".Y"), playerdata.getDouble("homes." + name + ".Z"));
    }
    public int getHomeAmount(){
        int result;
        if (playerdata.get("homes") == null){
            result = 0;
        }else{
            result = playerdata.getConfigurationSection("homes").getKeys(false).size();
        }
        return result;
    }
    public boolean hasHome(){
        boolean result = false;
        if (playerdata.get("homes") != null && playerdata.getConfigurationSection("homes").getKeys(false).size() > 0){
            result = true;
        }
        return result;
    }
    public boolean hasHome(String home){
        return playerdata.get("homes." + home) != null;
    }
    public void removeHome(String home) throws IOException {
        playerdata.set("homes."+ home, null);
        save();
    }
    public Set<String> getHomes(){
        return playerdata.getConfigurationSection("homes").getKeys(false);
    }
    public ItemStack getKillsItem(){
        List<String> lorelist = new ArrayList<>();
        ItemStack kitem = new ItemStack(Material.ARROW);
        ItemMeta meta = kitem.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Kills: ");
        lorelist.add(ChatColor.BLUE + playerdata.get("kills").toString());
        meta.setLore(lorelist);
        kitem.setItemMeta(meta);
        return kitem;
    }
    public ItemStack getDeathsItem(){
        List<String> lorelist = new ArrayList<>();
        ItemStack kitem = new ItemStack(Material.ARROW);
        ItemMeta meta = kitem.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Deaths: ");
        lorelist.add(ChatColor.BLUE + playerdata.get("deaths").toString());
        meta.setLore(lorelist);
        kitem.setItemMeta(meta);
        return kitem;
    }
}
