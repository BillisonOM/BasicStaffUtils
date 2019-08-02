package dev.d0tjar.api.apis.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Chat {
    /*
    All api's are created by yours truly, D0tJar.
     */
    public static String translate(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(Chat.translate(message));
    }
    public static void sendLongMessage(Player player, List<String> list){
        for(String s : list){
            player.sendMessage(Chat.translate(s.replaceAll("%player%", player.getName())));
        }
    }
    public static void broadcastLongMessage(List<String> list){
        for(String s : list){
            Bukkit.broadcastMessage(Chat.translate(s));
        }
    }
    public static void permissionOnlySendLong(String permission, List<String> list){
        for(Player all : Bukkit.getOnlinePlayers()){
            if(all.hasPermission(permission)){
                for(String s : list){
                    all.sendMessage(Chat.translate(s));
                }
            }
        }
    }
}
