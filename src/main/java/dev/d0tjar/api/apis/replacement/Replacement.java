package dev.d0tjar.api.apis.replacement;

import java.util.HashMap;
import java.util.Map;

public class Replacement {
    private Map<Object, Object> replacements = new HashMap<>();
    private String message;
    public Replacement(String message){
        this.message = message;
    }
    public Replacement add(Object Replace, Object This){
        replacements.put(Replace, This);
        return this;
    }
    public String toString(){
        replacements.keySet().forEach(Replace -> {
            message = message.replaceAll(Replace.toString(), replacements.get(Replace).toString());
        });
        return this.message;
    }
}
