package net.zwet.publickingdom.tabcompletions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length < 2) {
            List<String> commands = new ArrayList<>();
            if (args[0].equalsIgnoreCase("a")){
                commands.add("ally");
            }else if (args[0].equalsIgnoreCase("e")){
                commands.add("enemy");
            }else if (args[0].equalsIgnoreCase("i")){

            }

            commands.add("ally");
            commands.add("enemy");
            commands.add("info");
            commands.add("spawn");
            commands.add("invite");
            commands.add("kick");
            commands.add("join");
            commands.add("kick");
            commands.add("list");
            commands.add("setspawn");
            commands.add("setrank");
            commands.add("toggleboard");
            commands.add("hertogdom");
            commands.add("sethertogdom");
            if (sender.hasPermission("kingdom.home")) {
                commands.add("sethome");
                commands.add("home");
            }
            if (sender.hasPermission("publickingdom.staff")) {
                commands.add("set");
                commands.add("define");
                commands.add("clean");
                commands.add("hertogdomdefine");
                commands.add("rename");
            }
        return commands;
        }

    return null;
    }
}
