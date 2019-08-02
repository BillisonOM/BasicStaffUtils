package dev.d0tjar.api.apis.event;

import dev.d0tjar.api.apis.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Event {
    /*
    All api's are created by yours truly, D0tJar.
     */
    public Event(Listener listener, JavaPlugin plugin, String name){
        try {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        } catch (Exception ex){
        }
    }
}
