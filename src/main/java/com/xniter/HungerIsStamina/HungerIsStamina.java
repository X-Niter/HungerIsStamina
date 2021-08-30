package com.xniter.HungerIsStamina;

import org.bukkit.plugin.java.JavaPlugin;

public class HungerIsStamina extends JavaPlugin {
    public static HungerIsStamina plugin;
    public final Config config = new Config();

    public HungerIsStamina() {
    }

    public void onLoad() {
        plugin = this;
    }

    public void onEnable() {
        this.saveDefaultConfig();
        this.config.load(this.getConfig());
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
