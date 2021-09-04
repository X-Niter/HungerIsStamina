package com.xniter.HungerIsStamina.configuration;

import com.xniter.HungerIsStamina.HungerIsStamina;
import lombok.Getter;

public class Files {

    @Getter
    private ConfigFile settings;
    @Getter
    private ConfigFile messages;
    @Getter
    private ConfigFile foods;

    public Files() {
        load();
    }

    public void load() {
        settings = new ConfigFile(HungerIsStamina.getInstance(), "Settings.yml");
        messages = new ConfigFile(HungerIsStamina.getInstance(), "Messages.yml");
        foods = new ConfigFile(HungerIsStamina.getInstance(), "Foods.yml");
    }
}