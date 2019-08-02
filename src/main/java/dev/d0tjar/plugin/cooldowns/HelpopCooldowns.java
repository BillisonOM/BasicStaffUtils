package dev.d0tjar.plugin.cooldowns;

import dev.d0tjar.plugin.Utils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HelpopCooldowns {
    @Getter private static List<HelpopCooldowns> cooldowns = new ArrayList<>();
    private int cooldown = Utils.getConfiguration().getInt("HELPOP_COMMAND.COOLDOWN");
    private Player player;
    public HelpopCooldowns(Player player){
        this.player = player;
        cooldowns.add(this);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(cooldown != 0) {
                    cooldown--;
                } else {
                    delete();
                    cancel();
                }
            }
        }.runTaskTimer(Utils.getInstance(), 0, 20);
    }
    public int getCooldown(){
        return cooldown;
    }
    public void delete(){
        cooldowns.remove(this);
    }
    public Player getPlayer(){
        return player;
    }
    public static HelpopCooldowns getByPlayer(Player player){
        for(HelpopCooldowns cooldowns : HelpopCooldowns.getCooldowns()){
            if(cooldowns.getPlayer().equals(player)){
                return cooldowns;
            }
        }
        return null;
    }
}
