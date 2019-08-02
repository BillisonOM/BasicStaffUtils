package dev.d0tjar.plugin.commands;

import dev.d0tjar.api.apis.chat.Chat;
import dev.d0tjar.plugin.Utils;
import dev.d0tjar.plugin.cooldowns.HelpopCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(args.length == 0){
                Utils.getConfiguration().getStringList("HELPOP_COMMAND.FAIL").forEach(m ->{
                    player.sendMessage(Chat.translate(m));
                });
            } else {
                if (HelpopCooldowns.getByPlayer(player) != null) {
                    Utils.getConfiguration().getStringList("HELPOP_COMMAND.COOLDOWN_MESSAGE").forEach(m ->{
                        player.sendMessage(Chat.translate(m
                        .replaceAll("%sender%", player.getName())
                        .replaceAll("%cooldown%", HelpopCooldowns.getByPlayer(player).getCooldown() + "")));
                    });
                } else {
                    new HelpopCooldowns(player);
                    String request = "";
                    for (int i = 0; i < args.length; i++) {
                        request = request + args[i] + " ";
                    }
                    final String reason = request;
                    Utils.getConfiguration().getStringList("HELPOP_COMMAND.SUCCESS").forEach(m -> {
                        player.sendMessage(Chat.translate(m
                                .replaceAll("%sender%", player.getName())
                                .replaceAll("%request%", reason)));
                    });
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.hasPermission("basicstaff.staff.helpops")) {
                            Utils.getConfiguration().getStringList("HELPOP_COMMAND.STAFF_PROMPT").forEach(m -> {
                                all.sendMessage(Chat.translate(m
                                        .replaceAll("%sender%", player.getName())
                                        .replaceAll("%request%", reason)));
                            });
                        }
                    }
                    Utils.getConfiguration().getStringList("HELPOP_COMMAND.STAFF_PROMPT").forEach(m -> {
                        Bukkit.getConsoleSender().sendMessage(Chat.translate(m
                                .replaceAll("%sender%", player.getName())
                                .replaceAll("%request%", reason)));
                    });
                }
            }
        } else {
            commandSender.sendMessage(Chat.translate("&cThis command is for the use of players only!"));
        }
        return true;
    }
}
