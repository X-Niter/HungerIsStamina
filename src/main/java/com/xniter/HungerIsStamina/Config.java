package com.xniter.HungerIsStamina;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public Config() {
    }

    public void load(FileConfiguration c) {
    }

    public static Config get() {
        return HungerIsStamina.plugin.config;
    }
}
