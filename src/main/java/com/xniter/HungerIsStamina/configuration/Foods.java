package com.xniter.HungerIsStamina.configuration;

import com.xniter.HungerIsStamina.HungerIsStamina;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;


public class Foods {

    private static void setDefaults() {
        addConfigFoods("apple", 2, 0);
        addConfigFoods("baked_potato", 5, 0);
        addConfigFoods("beetroot", 1, 0);
        addConfigFoods("beetroot_soup", 6, 0);
        addConfigFoods("bread", 5, 0);
        addConfigFoods("cake", 2, 0);
        addConfigFoods("carrot", 3, 0);
        addConfigFoods("chorus_fruit", 4, 0);
        addConfigFoods("cooked_beef", 8, 0);
        addConfigFoods("cooked_chicken", 6, 0);
        addConfigFoods("cooked_cod", 5, 0);
        addConfigFoods("cooked_mutton", 6f, 0);
        addConfigFoods("cooked_porkchop", 8, 0);
        addConfigFoods("cooked_rabbit", 5, 0);
        addConfigFoods("cooked_salmon", 6, 0);
        addConfigFoods("cookie", 2, 0);
        addConfigFoods("dried_kelp", 1, 0);
        addConfigFoods("enchanted_golden_apple", 4, 0);
        addConfigFoods("golden_apple", 4, 0);
        addConfigFoods("golden_carrot", 6, 0);
        addConfigFoods("honey_bottle", 6, 0);
        addConfigFoods("melon_slice", 2, 0);
        addConfigFoods("mushroom_stew", 6, 0);
        addConfigFoods("poisonous_potato", 2, 4);
        addConfigFoods("potato", 1, 0);
        addConfigFoods("pufferfish", 1, 0);
        addConfigFoods("pumpkin_pie", 8, 0);
        addConfigFoods("rabbit_stew", 10, 0);
        addConfigFoods("beef", 3, 0);
        addConfigFoods("chicken", 2, 0);
        addConfigFoods("cod", 2, 0);
        addConfigFoods("mutton", 2, 0);
        addConfigFoods("porkchop", 3, 0);
        addConfigFoods("rabbit", 3, 0);
        addConfigFoods("salmon", 2, 0);
        addConfigFoods("rotten_flesh", 4, 2);
        addConfigFoods("spider_eye", 2, 2);
        addConfigFoods("suspicious_stew", 6, 1);
        addConfigFoods("sweet_berries", 2, 0);
        addConfigFoods("tropical_fish", 1, 0);
    }

    @Getter
    private final String name;

    @Getter
    @Setter
    private float food;

    @Getter
    @Setter
    private float damage;

    Foods(String name, float food, float damage) {
        this.name = name;
        this.food = food;
        this.damage = damage;
    }


    public static void load() {

        FileConfiguration foods = HungerIsStamina.getInstance().getFiles().getFoods().getFileConfiguration();

        setDefaults();


        HungerIsStamina.getInstance().getFiles().getFoods().save();
    }

    // Small function for adding food item in the config
    private static void addConfigFoods(String name, float food, float damage) {
        FileConfiguration foods = HungerIsStamina.getInstance().getFiles().getFoods().getFileConfiguration();
        foods.addDefault(name + ".food", food);
        foods.addDefault(name + ".damage", damage);
    }
}

