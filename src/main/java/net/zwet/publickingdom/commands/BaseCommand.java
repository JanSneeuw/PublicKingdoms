package net.zwet.publickingdom.commands;

import net.zwet.publickingdom.PublicKingdom;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BaseCommand implements CommandExecutor {

    private PublicKingdom main;
    private Ally ally;
    private Enemy enemy;
    private Help help;
    private Info info;
    private Invite invite;
    private Join join;
    private Kick kick;
    private List list;
    private Set set;
    private Setrank setrank;
    private Setspawn setspawn;
    private Spawn spawn;
    private Define define;
    private ScoreboardToggle scoreboardToggle;
    private Home home;
    private Sethome sethome;
    private Clean clean;
    private Rename rename;
    private Hertogdom hertogdom;
    private HertogdomDefine hertogdomDefine;
    private SetHertogdomSpawn setHertogdomSpawn;
    private RemoveHome removeHome;
    private ListHomes listHomes;

    public BaseCommand(PublicKingdom PublicKingdom){
        this.main = PublicKingdom;
        this.ally = new Ally(this.main);
        this.enemy = new Enemy(this.main);
        this.help = new Help(this.main);
        this.info = new Info(this.main);
        this.invite = new Invite(this.main);
        this.join = new Join(this.main);
        this.kick = new Kick(this.main);
        this.list = new List(this.main);
        this.set = new Set(this.main);
        this.setrank = new Setrank(this.main);
        this.setspawn = new Setspawn(this.main);
        this.spawn = new Spawn(this.main);
        this.define = new Define(this.main);
        this.scoreboardToggle = new ScoreboardToggle(this.main);
        this.home = new Home(this.main);
        this.clean = new Clean();
        this.sethome = new Sethome(this.main);
        this.rename = new Rename();
        this.hertogdom = new Hertogdom(this.main);
        this.hertogdomDefine = new HertogdomDefine(this.main);
        this.setHertogdomSpawn = new SetHertogdomSpawn(this.main);
        this.removeHome = new RemoveHome(this.main);
        this.listHomes = new ListHomes(this.main);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("Ally")) {
                this.ally.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Enemy")) {
                this.enemy.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Help")) {
                this.help.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Info")) {
                this.info.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Invite")) {
                this.invite.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Join")) {
                this.join.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Kick")) {
                this.kick.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("List")) {
                this.list.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Set")) {
                this.set.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("setrank")) {
                this.setrank.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Setspawn")) {
                this.setspawn.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Spawn")) {
                this.spawn.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            } else if (args[0].equalsIgnoreCase("Define")) {
                this.define.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("S")){
                this.spawn.onCommand(sender, command, label, Arrays.copyOfRange(args, 1 , args.length));
            }else if (args[0].equalsIgnoreCase("toggleboard")){
                this.scoreboardToggle.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("home")){
                this.home.onCommand(sender, command, label , Arrays.copyOfRange(args, 1 , args.length));
            }else if (args[0].equalsIgnoreCase("sethome")){
                this.sethome.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("clean")){
                this.clean.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("rename")){
                this.rename.onCommand(sender,command,label,Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("hertogdom")){
                this.hertogdom.onCommand(sender,command,label,Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("hertogdomdefine")){
                this.hertogdomDefine.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("sethertogdomspawn")){
                this.setHertogdomSpawn.onCommand(sender, command,label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("removehome")){
                this.removeHome.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }else if (args[0].equalsIgnoreCase("listhomes")){
                this.listHomes.onCommand(sender, command, label, Arrays.copyOfRange(args, 1 , args.length));
            }
        }else{
            if (sender instanceof Player){
                Player player = (Player) sender;
                player.sendMessage(ChatColor.RED + "§c§lFire§e§lKingdom §7- Deze plugin is gemaakt door JanSneeuw!");
                player.sendMessage(ChatColor.RED + "§c§lFire§e§lKingdom §7- Gebruik /k help voor meer informatie!");
            }
        }
        return true;
    }
}

