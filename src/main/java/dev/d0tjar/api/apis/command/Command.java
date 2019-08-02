package dev.d0tjar.api.apis.command;

import dev.d0tjar.api.apis.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Command {
    /*
    All api's are created by yours truly, D0tJar.
     */
    public Command(String name, CommandExecutor executor, TabCompleter completer, List<String> alias, JavaPlugin plugin){
        try {
            plugin.getCommand(name).setExecutor(executor);
            if(completer != null){
                plugin.getCommand(name).setTabCompleter(completer);
            }
            if(alias != null){
                plugin.getCommand(name).setAliases(alias);
            }
        } catch (Exception ex){
        }
    }
}
