package dev.d0tjar.plugin.commands;

import dev.d0tjar.api.apis.chat.Chat;
import dev.d0tjar.plugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(player.hasPermission("basicstaff.staff.staffchat")){
                if(args.length == 0){
                    if(Utils.getStaffChatMembers().contains(player)){
                        Utils.getStaffChatMembers().remove(player);
                        Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.TOGGLE_OFF").forEach(m ->{
                            player.sendMessage(Chat.translate(m));
                        });
                    } else {
                        Utils.getStaffChatMembers().add(player);
                        Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.TOGGLE_ON").forEach(m ->{
                            player.sendMessage(Chat.translate(m));
                        });
                    }
                } else {
                    String message = "";
                    for(int i = 0; i < args.length; i++){
                        message = message + args[i] + " ";
                    }
                    final String chatM = message;
                    Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.STAFF_PROMPT").forEach(m ->{
                        Bukkit.getConsoleSender().sendMessage(Chat.translate(m
                                .replaceAll("%sender%", player.getName())
                                .replaceAll("%message%", chatM)));
                    });
                    for(Player all : Bukkit.getOnlinePlayers()){
                        if(all.hasPermission("basicstaff.staff.staffchat")){
                            Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.STAFF_PROMPT").forEach(m ->{
                                all.sendMessage(Chat.translate(m
                                .replaceAll("%sender%", player.getName())
                                .replaceAll("%message%", chatM)));
                            });
                        }
                    }
                }
            } else {
                player.sendMessage(Chat.translate("&c&oYou do not have the right permissions to run that command!"));
            }
        } else {
            if(args.length == 0){
                commandSender.sendMessage(Chat.translate("&cUsage: /sc <message...>"));
            } else {
                String message = "";
                for(int i = 0; i < args.length; i++){
                    message = message + args[i] + " ";
                }
                final String chatM = message;
                Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.STAFF_PROMPT").forEach(m ->{
                    Bukkit.getConsoleSender().sendMessage(Chat.translate(m
                            .replaceAll("%sender%", commandSender.getName())
                            .replaceAll("%message%", chatM)));
                });
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(all.hasPermission("basicstaff.staff.staffchat")){
                        Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.STAFF_PROMPT").forEach(m ->{
                            all.sendMessage(Chat.translate(m
                                    .replaceAll("%sender%", commandSender.getName())
                                    .replaceAll("%message%", chatM)));
                        });
                    }
                }
            }
        }
        return true;
    }
}
