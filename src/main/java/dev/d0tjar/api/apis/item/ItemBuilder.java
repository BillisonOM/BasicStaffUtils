package dev.d0tjar.api.apis.item;

import dev.d0tjar.api.apis.chat.Chat;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    /*
    All api's are created by yours truly, D0tJar.
     */
    private ItemStack itemStack;
    private ItemMeta meta;
    private Material material;
    private String name;
    public ItemBuilder(Material mat, String name){
        this.material = mat;
        this.itemStack = new ItemStack(material);
        this.meta = this.itemStack.getItemMeta();
        this.name = name;
        this.meta.setDisplayName(Chat.translate(this.name));
    }
    public ItemBuilder setDurability(short durability){
        itemStack.setDurability(durability);
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }
    public ItemBuilder setLore(List<String> lore){
        List<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(Chat.translate(s));
        }
        this.meta.setLore(list);
        return this;
    }
    public ItemBuilder setLore(String... lore){
        List<String> list = new ArrayList<>();
        for(String s : lore){
            list.add(Chat.translate(s));
        }
        this.meta.setLore(list);
        return this;
    }
    public ItemStack getStack(){
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }
}
