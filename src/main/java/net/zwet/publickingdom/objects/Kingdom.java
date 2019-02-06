package net.zwet.publickingdom.objects;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import org.bukkit.*;
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
import java.util.UUID;

public class Kingdom {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("PublicKingdom");
    FileConfiguration kingdomdata = null;
    File kingdomdatafile = null;
    public Kingdom(Player p){
        Playerdata playerdata = new Playerdata(p);
        kingdomdatafile = new File(plugin.getDataFolder() + File.separator + "kingdoms" + File.separator + playerdata.getKingdomName() + ".yml");
        if (playerdata.isInKingdom()) {
            kingdomdata = YamlConfiguration.loadConfiguration(kingdomdatafile);
        }
    }
    public Kingdom(String kingdom) throws NoSuchKingdomException {
        kingdomdatafile = new File(plugin.getDataFolder() + File.separator + "kingdoms" + File.separator + kingdom + ".yml");
        if (kingdomdatafile.exists()) {
            kingdomdata = YamlConfiguration.loadConfiguration(kingdomdatafile);
        }else{
            throw new NoSuchKingdomException();
        }
    }

    public String getName(){
        return kingdomdata.getString("naam");
    }
    public ItemStack getNameItem(){
        List<String> namelore = new ArrayList<>();
        ItemStack name = new ItemStack(Material.NAME_TAG);
        ItemMeta namedisplay = name.getItemMeta();
        namedisplay.setDisplayName(ChatColor.GRAY + "Naam:");
        namelore.add(ChatColor.BLUE + getName());
        namedisplay.setLore(namelore);
        name.setItemMeta(namedisplay);
        return name;
    }
    public String getColoredName(){
        return ChatColor.translateAlternateColorCodes('&', kingdomdata.getString("prefix").replace("-", ""));
    }
    public String getDefaultRank(){
        return kingdomdata.getString("Default-Rank");
    }
    public ProtectedRegion getRegion(){
        ProtectedRegion region = WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getRegion(kingdomdata.getString("region"));
        return region;
    }
    public List<String> getAllies(){
        return kingdomdata.getStringList("allies");
    }
    public List<String> getEnemies(){
        return kingdomdata.getStringList("enemies");
    }
    public String getAlliesString(){
        String allylist;
        if (hasAllies()) {
            StringBuilder allybuilder = new StringBuilder();
            for (String allies : getAllies()) {
                allybuilder.append(allies);
            }
            allylist = allybuilder.toString().trim();
        }else{
            allylist = "GEEN";
        }
        return allylist;
    }
    public ItemStack getAlliesItem(){
        List<String> allylore = new ArrayList<>();

        ItemStack allyitem = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta allyitemmeta = allyitem.getItemMeta();
        allyitemmeta.setDisplayName(ChatColor.GRAY + "Bondgenoten: ");
        if (!hasAllies()){
            allylore.add(ChatColor.BLUE + "GEEN");
        }else{
            for (String ally : getAllies()) {
                allylore.add(ChatColor.BLUE + ally);
            }
        }
        allyitemmeta.setLore(allylore);
        allyitem.setItemMeta(allyitemmeta);
        return allyitem;
    }
    public String getEnemiesString(){
        String enemylist;
        if (hasEnemies()) {
            StringBuilder enemiesbuilder = new StringBuilder();
            for (String enemies : getEnemies()) {
                enemiesbuilder.append(enemies );
            }
            enemylist = enemiesbuilder.toString().trim();
        }else{
            enemylist = "GEEN";
        }
        return enemylist;
    }
    public ItemStack getEnemiesItem(){
        List<String> enemylore = new ArrayList<>();
        ItemStack enemyitem = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta enemyitemmeta = enemyitem.getItemMeta();
        enemyitemmeta.setDisplayName(ChatColor.GRAY + "Vijanden: ");
        if (!hasEnemies()){
            enemylore.add(ChatColor.BLUE + "GEEN");
        }else {
            for (String enemy : getEnemies()) {
                enemylore.add(ChatColor.BLUE + enemy);
            }
        }
        enemyitemmeta.setLore(enemylore);
        enemyitem.setItemMeta(enemyitemmeta);
        return enemyitem;
    }
    public boolean hasAllies(){
        return getAllies().size() != 0 && getAllies() != null;
    }
    public boolean maxAlliesReached(){
        return plugin.getConfig().get("MaxAllies") != null && getAllies().size() >= plugin.getConfig().getInt("MaxAllies");
    }
    public boolean maxEnemiesReached(){
        return plugin.getConfig().get("MaxEnemies") != null && getEnemies().size() >= plugin.getConfig().getInt("MaxEnemies");
    }
    public boolean hasEnemies(){
        return getEnemies().size() != 0 && getEnemies() != null;
    }
    public boolean exists(){
        return kingdomdata != null;
    }
    public List<String> getChildren(String rank){
        return kingdomdata.getStringList("ranks." + rank + ".children");
    }
    public boolean isAllyWith(Kingdom k){
        return getAllies().contains(k.getName());
    }
    public boolean isEnemyWith(Kingdom k){
        return getEnemies().contains(k.getName());
    }

    public List getRankPermissions(String rank){
        return kingdomdata.getStringList("ranks." + rank + ".perms");
    }
    public void addAlly(Kingdom kingdom){
        if (kingdom.exists()){
            List allylist = getAllies();
            allylist.add(kingdom.getName());
            kingdomdata.set("allies", allylist);
            try {
                kingdomdata.save(kingdomdatafile);
            } catch (IOException e) {
                Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na adden van ally!");
            }
        }
    }
    public void addEnemy(Kingdom kingdom){
        if (kingdom.exists()){
            List enemylist = getEnemies();
            enemylist.add(kingdom.getName());
                kingdomdata.set("enemies", enemylist);
                try {
                    kingdomdata.save(kingdomdatafile);
                } catch (IOException e) {
                    Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na adden van enemy");
                }
            }
        }
    public void removeAlly(Kingdom kingdom){
        List allylist = getAllies();
        allylist.remove(kingdom.getName());
        kingdomdata.set("allies", allylist);
        try {
            kingdomdata.save(kingdomdatafile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na verwijderen van ally!");
        }
    }
    public void removeEnemy(Kingdom kingdom){
        List enemylist = getEnemies();
        enemylist.remove(kingdom.getName());
        kingdomdata.set("enemies", enemylist);
        try {
            kingdomdata.save(kingdomdatafile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na verwijderen van enemy");
        }
    }
    public void setRegion(Player p){
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        for (ProtectedRegion region : WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getApplicableRegions(p.getLocation())){
            kingdomdata.set("region", region.getId());
            try {
                kingdomdata.save(kingdomdatafile);
            } catch (IOException e) {
                Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na veranderen van region!");
            }
            p.sendMessage(fireprefix + " " + ChatColor.BLUE + region.getId() + " is nu de region van " + getName());

        }

    }
    public void setRegion(String region) {
        if (WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getRegion(region) != null) {
            kingdomdata.set("region", region);
            try {
                kingdomdata.save(kingdomdatafile);
            } catch (IOException e) {
                Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na veranderen van region");
            }
        }else{
            throw new NullPointerException("Er is geen region genaamt " + '"' + region + '"');
        }
    }
    public String getPrefix(){
        String prefix = kingdomdata.getString("prefix").replace("-", "");
        return prefix;
    }
    public String getPlayersString(){
        String playersstring;
        File folder = new File(plugin.getDataFolder() + File.separator + "players");
        File[] files = folder.listFiles();
        YamlConfiguration players = new YamlConfiguration();
        StringBuilder playerlijst = new StringBuilder();
        for (File fs : files){
            try {
                players.load(fs);

                if (players.get("kingdom").equals(getName())){
                    playerlijst.append(players.get("naam")).append(", ");
                }
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        if (playerlijst.length() != 0) {
            playersstring = playerlijst.toString();
        }else{
            playersstring = "GEEN";
        }
        return playersstring;
    }
    public ItemStack getPlayersItem(){
        List<String> playerslore = new ArrayList<>();
        ItemStack players = new ItemStack(Material.JACK_O_LANTERN);
        ItemMeta playersmeta = players.getItemMeta();
        playersmeta.setDisplayName(ChatColor.GRAY + "Spelers: ");
        if (getPlayersString().equalsIgnoreCase("GEEN")){
            playerslore.add(getPlayersString());
        }else {
            String[] ps = getPlayersString().split(", ");
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ps.length; i++) {
                if (i % 4 == 0) {
                    builder.append(ps[i]).append(", ");
                    playerslore.add(ChatColor.BLUE + builder.toString());
                    builder.setLength(0);
                } else if (i + 1 > ps.length) {
                    if (builder.toString().endsWith(", ") || builder.toString().endsWith(",")) {
                        playerslore.add(builder.toString().substring(0, builder.toString().length() - 1));
                    } else {
                        playerslore.add(ChatColor.BLUE + builder.toString());
                    }
                } else {
                    builder.append(ChatColor.BLUE + ps[i]).append(", ");
                }
            }
        }
        playersmeta.setLore(playerslore);
        players.setItemMeta(playersmeta);
        return players;
    }
    public void clean() throws IOException, InvalidConfigurationException {
        File[] pfiles = new File(plugin.getDataFolder() + File.separator + "players").listFiles();
        YamlConfiguration pdatas = new YamlConfiguration();
        assert pfiles != null;
        for (File playerfiles : pfiles){
            pdatas.load(playerfiles);
            if (pdatas.getString("kingdom").equalsIgnoreCase(getName())){
                pdatas.set("kingdom", plugin.getConfig().getString("Kingdomloos"));
                pdatas.set("rank", "NONE");
                pdatas.save(playerfiles);
            }
        }
    }
    public Playerdata getKing(){
        return new Playerdata(UUID.fromString(kingdomdata.getString("King")));
    }
    public ItemStack getKingItem(){
        List<String> kinglore = new ArrayList<>();
        ItemStack king = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta kingmeta = king.getItemMeta();
        kingmeta.setDisplayName(ChatColor.GRAY + "Koning: ");
        kinglore.add(ChatColor.BLUE + getKing().getName());
        kingmeta.setLore(kinglore);
        king.setItemMeta(kingmeta);
        return king;
    }
    public int getPlayersAmount(){
        File folder = new File(plugin.getDataFolder() + File.separator + "players");
        File[] files = folder.listFiles();
        YamlConfiguration hs = new YamlConfiguration();
        int ledenaantal = 0;
        for (File pls : files) {
            try {
                hs.load(pls);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            if (hs.getString("kingdom").equalsIgnoreCase(kingdomdata.getString("naam"))) {
                ledenaantal += 1;
            }
        }
        return ledenaantal;
    }
    public YamlConfiguration getYaml() throws IOException, InvalidConfigurationException {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.load(kingdomdatafile);
        return yaml;
    }
    public boolean hasRank(String rank){
        return kingdomdata.get("ranks." + rank) != null;
    }
    public boolean rankLimitReached(String rank){
        File[] playerfiles = new File(plugin.getDataFolder() + File.separator + "players").listFiles();
        YamlConfiguration pfs = new YamlConfiguration();
        int ranklimit = 0;
        for (File playerfs : playerfiles) {
            try {
                pfs.load(playerfs);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            if (kingdomdata.get("ranks." + rank + ".limit") != null) {
                if (pfs.getString("rank").equalsIgnoreCase(rank)) {
                    if (pfs.getString("kingdom").equalsIgnoreCase(kingdomdata.getString("naam"))) {
                        ranklimit += 1;
                    }
                }
            }
        }
        return (kingdomdata.get("ranks." + rank + ".limit") == null || kingdomdata.getInt("ranks." + rank + ".limit") <= ranklimit) && kingdomdata.get("ranks." + rank + ".limit") != null;
    }
    public void setSpawn(int xcord, int ycord, int zcord) throws IOException {
        kingdomdata.set("spawn.X", xcord);
        kingdomdata.set("spawn.Y", ycord);
        kingdomdata.set("spawn.Z", zcord);
        save();
    }
    public void setSpawn(Player player) throws IOException {
        kingdomdata.set("spawn.X", player.getLocation().getX());
        kingdomdata.set("spawn.Y", player.getLocation().getY());
        kingdomdata.set("spawn.Z", player.getLocation().getZ());
        save();
    }
    public void save() throws IOException {
        kingdomdata.save(kingdomdatafile);
    }
    public Location getSpawn(){
        World world = Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"));
        int X = kingdomdata.getInt("spawn.X");
        int Y = kingdomdata.getInt("spawn.Y");
        int Z = kingdomdata.getInt("spawn.Z");
        return new Location(world, X, Y, Z);
    }
    public List<String> getFlags(){
        return kingdomdata.getStringList("flags");
    }
    public boolean hasFlag(String flag){
        return getFlags().contains(flag);
    }
    public String getColoredRank(String rank){
        return ChatColor.translateAlternateColorCodes('&', kingdomdata.getString("ranks." + rank + ".prefix"));
    }
    public void rename(String name) throws IOException {
        if (!name.equalsIgnoreCase(getName())){
            File newKingdom = new File(plugin.getDataFolder() + File.separator + "kingdoms" + File.separator + name + ".yml");
            kingdomdatafile.renameTo(newKingdom);
            for (File files : new File(plugin.getDataFolder() + File.separator + "players").listFiles()){
                Playerdata pd = new Playerdata(files);
                if (pd.getKingdomName().equalsIgnoreCase(getName())){
                    pd.setKingdom(name);
                    save();
                }
            }

        }
    }
    public void defineHertogdom(String naam, String region){
        if (WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getRegion(region) != null) {
            kingdomdata.set("Hertogdom."+ naam + ".Region", region);
            try {
                kingdomdata.save(kingdomdatafile);
            } catch (IOException e) {
                Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na veranderen van region");
            }
        }else{
            throw new NullPointerException("Er is geen region genaamt " + '"' + region + '"');
        }
    }
    public void defineHertogdom(String naam, Player p){
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        for (ProtectedRegion region : WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getApplicableRegions(p.getLocation())){
            kingdomdata.set("Hertogdom." + naam + ".Region", region.getId());
            try {
                kingdomdata.save(kingdomdatafile);
            } catch (IOException e) {
                Bukkit.getLogger().warning("Kan " + getName() + "'s kingdomdatafile niet opslaan na veranderen van region!");
            }
            p.sendMessage(fireprefix + " " + ChatColor.BLUE + region.getId() + " is nu de region van " + getName());

        }
    }
    public ProtectedRegion getHertogdomRegion(String naam){
        return WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getRegion(kingdomdata.getString("Hertogdom."+ naam + ".Region"));
    }
    public boolean hasHertogdom(){
        return kingdomdata.get("Hertogdom") != null;
    }
    public Location getHertogdomLocation(String naam){
        Location location = null;
        if (hasHertogdom()){
            location = new Location(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World")), kingdomdata.getInt("Hertogdom." + naam + ".X"), kingdomdata.getInt("Hertogdom." + naam + ".Y"), kingdomdata.getInt("Hertogdom." + naam + ".Z"));
        }
        return location;
    }
    public void setHertogdomLocation(String naam, int xcord, int ycord, int zcord) throws IOException {
        kingdomdata.set("Hertogdom." + naam + ".X", xcord);
        kingdomdata.set("Hertogdom." + naam + ".Y", ycord);
        kingdomdata.set("Hertogdom." + naam + ".Z", zcord);
        save();
    }
    public void setHertogdomLocation(String naam, Player p) throws IOException {
        kingdomdata.set("Hertogdom." + naam + ".X", p.getLocation().getX());
        kingdomdata.set("Hertogdom." + naam + ".Y", p.getLocation().getY());
        kingdomdata.set("Hertogdom." + naam + ".Z", p.getLocation().getZ());
        save();
    }
    public boolean hertogdomExists(String naam){
        return kingdomdata.get("Hertogdom." + naam) != null;
    }
    public boolean hasRegion(ProtectedRegion region){
        boolean answer = false;
        if (hasHertogdom()) {
            for (String hertogdom : kingdomdata.getConfigurationSection("Hertogdom").getKeys(false)) {
                if (WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getRegion(kingdomdata.getString("Hertogdom." + hertogdom + ".Region")) != null) {
                    ProtectedRegion protectedRegion = WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getRegion(kingdomdata.getString("Hertogdom." + hertogdom + ".Region"));
                    assert protectedRegion != null;
                    if (protectedRegion.getId().equalsIgnoreCase(region.getId())) {
                        answer = true;
                    }
                }
            }
        }
        return answer;
    }
    public ItemStack getKillsItem(){
        int kills = 0;
        List<String> lorelist = new ArrayList<>();
        File[] files = new File(plugin.getDataFolder() + File.separator + "players").listFiles();
        for (File pfiles : files){
            Playerdata playerdata = new Playerdata(pfiles);
            if (playerdata.isInKingdom()){
                if (playerdata.getKingdomName().equalsIgnoreCase(getName())){
                    kills += playerdata.getKills();
                }
            }
        }
        ItemStack kitem = new ItemStack(Material.ARROW);
        ItemMeta meta = kitem.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Kills: ");
        lorelist.add(ChatColor.BLUE + "" + kills);
        meta.setLore(lorelist);
        kitem.setItemMeta(meta);
        return kitem;
    }
    public ItemStack getDeathsItem(){
        int deaths = 0;
        List<String> lorelist = new ArrayList<>();
        File[] files = new File(plugin.getDataFolder() + File.separator + "players").listFiles();
        for (File pfiles : files){
            Playerdata playerdata = new Playerdata(pfiles);
            if (playerdata.isInKingdom()){
                if (playerdata.getKingdomName().equalsIgnoreCase(getName())){
                    deaths += playerdata.getDeaths();
                }
            }
        }
        ItemStack kitem = new ItemStack(Material.ARROW);
        ItemMeta meta = kitem.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Deaths: ");
        lorelist.add(ChatColor.BLUE + "" + deaths);
        meta.setLore(lorelist);
        kitem.setItemMeta(meta);
        return kitem;
    }

    public boolean hasApplication(String UUID){
        return getApplication(UUID) != null;
    }
    public void addApplication(Player player, String application) throws NoSuchKingdomException {
        Application appl = new Application(application, new Kingdom(getName()), player);
    }
    public boolean hasApplications(){
        File solKingdom = new File(plugin.getDataFolder() + File.separator + "applications" + File.separator + getName() + ".yml");
        FileConfiguration solKingdomConf = YamlConfiguration.loadConfiguration(solKingdom);
        boolean hasApplications = solKingdomConf.getKeys(false) == null;
        return hasApplications;
    }
    public String getApplication(String UUID){
        File solFile = new File(plugin.getDataFolder() + File.separator + "applications" + File.separator + getName() + ".yml");
        FileConfiguration solFileConf = YamlConfiguration.loadConfiguration(solFile);
        return solFileConf.getString(UUID);
    }
    public void sendApplications(Player player){
        if (hasApplications()){
            String name= null;
            FileConfiguration configuration = null;
            File solKingdom = new File(plugin.getDataFolder() + File.separator + "applications" + File.separator + getName() + ".yml");
            FileConfiguration solKingdomConf = YamlConfiguration.loadConfiguration(solKingdom);
            for (String application : solKingdomConf.getKeys(false)){
                for (File pfile : new File(plugin.getDataFolder() + File.separator + "players").listFiles()){
                    if (pfile.getName().equals(application)) {
                        Playerdata pd = new Playerdata(pfile);
                        name = pd.getName();
                        player.sendMessage(ChatColor.GOLD + name + ": " + getApplication(name));
                    }
                }
            }
        }
    }
}
