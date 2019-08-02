package dev.d0tjar.plugin;

import dev.d0tjar.api.apis.chat.Chat;
import dev.d0tjar.api.apis.config.Configuration;
import dev.d0tjar.plugin.commands.HelpopCommand;
import dev.d0tjar.plugin.commands.ReportCommand;
import dev.d0tjar.plugin.commands.SCCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Utils extends JavaPlugin implements org.bukkit.event.Listener {
    @Getter private static Utils instance;
    @Getter private static List<Player> staffChatMembers = new ArrayList<>();
    @Getter private static Configuration configuration;
    @Override
    public void onEnable() {
        instance = this;
        configuration = new Configuration("config.yml", this);
        Bukkit.getConsoleSender().sendMessage(Chat.translate("&cBasicStaffUtils by &4D0tJar &chas loaded!"));
        getCommand("helpop").setExecutor(new HelpopCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("sc").setExecutor(new SCCommand());
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(staffChatMembers.contains(player)){
            event.setCancelled(true);
            Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.STAFF_PROMPT").forEach(m ->{
                Bukkit.getConsoleSender().sendMessage(Chat.translate(m
                        .replaceAll("%sender%", player.getName())
                        .replaceAll("%message%", event.getMessage())));
            });
            for(Player all : Bukkit.getOnlinePlayers()){
                if(all.hasPermission("basicstaff.staff.staffchat")){
                    Utils.getConfiguration().getStringList("STAFF_CHAT_COMMAND.STAFF_PROMPT").forEach(m ->{
                        all.sendMessage(Chat.translate(m
                                .replaceAll("%sender%", player.getName())
                                .replaceAll("%message%", event.getMessage())));
                    });
                }
            }
        }
    }
}
