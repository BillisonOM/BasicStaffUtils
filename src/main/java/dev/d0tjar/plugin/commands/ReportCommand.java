package dev.d0tjar.plugin.commands;

import dev.d0tjar.api.apis.chat.Chat;
import dev.d0tjar.plugin.Utils;
import dev.d0tjar.plugin.cooldowns.ReportCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(args.length == 0 || args.length == 1){
                Utils.getConfiguration().getStringList("REPORT_COMMAND.FAIL").forEach(m ->{
                    player.sendMessage(Chat.translate(m));
                });
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Chat.translate("&cThis player is not online!"));
                } else {
                    if (ReportCooldowns.getByPlayer(player) != null) {
                        Utils.getConfiguration().getStringList("REPORT_COMMAND.COOLDOWN_MESSAGE").forEach(m ->{
                            player.sendMessage(Chat.translate(m
                            .replaceAll("%sender%", player.getName())
                            .replaceAll("%target%", target.getName())
                            .replaceAll("%cooldown%", ReportCooldowns.getByPlayer(player).getCooldown() + "")));
                        });
                    } else {
                        new ReportCooldowns(player);
                        String reason = "";
                        for (int i = 1; i < args.length; i++) {
                            reason = reason + args[i] + " ";
                        }
                        final String report = reason;
                        Utils.getConfiguration().getStringList("REPORT_COMMAND.SUCCESS").forEach(m -> {
                            player.sendMessage(Chat.translate(m
                                    .replaceAll("%target%", target.getName())
                                    .replaceAll("%reason%", report)
                                    .replaceAll("%sender%", player.getName())));
                        });
                        Utils.getConfiguration().getStringList("REPORT_COMMAND.STAFF_PROMPT").forEach(m -> {
                            Bukkit.getConsoleSender().sendMessage(Chat.translate(m
                                    .replaceAll("%target%", target.getName())
                                    .replaceAll("%sender%", player.getName())
                                    .replaceAll("%reason%", report)));
                        });
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.hasPermission("basicstaff.staff.reports")) {
                                Utils.getConfiguration().getStringList("REPORT_COMMAND.STAFF_PROMPT").forEach(m -> {
                                    all.sendMessage(Chat.translate(m
                                            .replaceAll("%target%", target.getName())
                                            .replaceAll("%sender%", player.getName())
                                            .replaceAll("%reason%", report)));
                                });
                            }
                        }
                    }
                }
            }
        } else {
            commandSender.sendMessage(Chat.translate("&cThis command is for the use of players only!"));
        }

        return true;
    }
}
