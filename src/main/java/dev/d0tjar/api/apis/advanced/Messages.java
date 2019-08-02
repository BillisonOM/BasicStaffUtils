package dev.d0tjar.api.apis.advanced;

import dev.d0tjar.api.apis.chat.Chat;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class Messages {
    private TextComponent component;
    public Messages(String message){
        this.component = new TextComponent(Chat.translate(message));
    }
    public Messages HoverText(String message){
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(message).create()));
        return this;
    }
    public Messages ClickUrl(String url){
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }
    public Messages ClickCommand(String cmd){
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        return this;
    }
    public TextComponent getComponent(){
        return component;
    }
}
