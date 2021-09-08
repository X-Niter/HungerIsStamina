package com.xniter.HungerIsStamina.Utilities;

import java.util.HashMap;
import java.util.Map;

public enum DefaultFood implements Labeled {

    apple("apple", 4, 0),
    baked_potato("baked_potato", 5, 0),
    beetroot("beetroot", 1, 0),
    beetroot_soup("beetroot_soup", 6, 0),
    bread("bread", 5, 0),
    cake("cake", 2, 0),
    carrot("carrot", 3, 0),
    chorus_fruit("chorus_fruit", 4, 0),
    cooked_beef("cooked_beef", 8, 0),
    cooked_chicken("cooked_chicken", 6, 0),
    cooked_cod("cooked_cod", 5, 0),
    cooked_mutton("cooked_mutton", 6, 0),
    cooked_porkchop("cooked_porkchop", 8, 0),
    cooked_rabbit("cooked_rabbit", 5, 0),
    cooked_salmon("cooked_salmon", 6, 0),
    cookie("cookie", 2, 0),
    dried_kelp("dried_kelp", 1, 0),
    enchanted_golden_apple("enchanted_golden_apple", 4, 0),
    golden_apple("golden_apple", 4, 0),
    golden_carrot("golden_carrot", 6, 0),
    honey_bottle("honey_bottle", 6, 0),
    melon_slice("melon_slice", 2, 0),
    mushroom_stew("mushroom_stew", 6, 0),
    poisonous_potato("poisonous_potato", 2, 4),
    potato("potato", 1, 0),
    pufferfish("pufferfish", 1, 0),
    pumpkin_pie("pumpkin_pie", 8, 0),
    rabbit_stew("rabbit_stew", 10, 0),
    beef("beef", 3, 0),
    chicken("chicken", 2, 0),
    cod("cod", 2, 0),
    mutton("mutton", 2, 0),
    porkchop("porkchop", 3, 0),
    rabbit("rabbit", 3, 0),
    salmon("salmon", 2, 0),
    rotten_flesh("rotten_flesh", 4, 2),
    spider_eye("spider_eye", 2, 2),
    suspicious_stew("suspicious_stew", 6, 0),
    sweet_berries("sweet_berries", 2, 0),
    tropical_fish("tropical_fish", 1, 0);


    private static final Map<String, DefaultFood> BY_LABEL = new HashMap<>();
    private static final Map<Integer, DefaultFood> BY_HUNGER = new HashMap<>();
    private static final Map<Integer, DefaultFood> BY_DAMAGE = new HashMap<>();

    static {
        for (DefaultFood food: values()) {
            BY_LABEL.put(food.label, food);
            BY_HUNGER.put(food.hungerValue, food);
            BY_DAMAGE.put(food.damageValue, food);
        }
    }

    public final String label;
    public final int hungerValue;
    public final int damageValue;

    DefaultFood(String label, int hungerValue, int damageValue) {
        this.label = label;
        this.hungerValue = hungerValue;
        this.damageValue = damageValue;
    }


    @Override
    public String label() {
        return label;
    }
    @Override
    public int hungerValue() {
        return hungerValue;
    }
    @Override
    public int damageValue() {
        return damageValue;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static DefaultFood valueOfName(String label) {
        return BY_LABEL.get(label);
    }

    public static DefaultFood valueOfHunger(int hunger) {
        return BY_HUNGER.get(hunger);
    }

    public static DefaultFood valueOfDamage(int damageValue) {
        return BY_DAMAGE.get(damageValue);
    }
}
