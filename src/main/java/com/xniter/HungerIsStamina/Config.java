package com.xniter.HungerIsStamina;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public boolean IsPluginEnabled;

    public boolean StaminaCostForSprintingEnabled;
    public int StaminaCostForSprinting;

    public boolean StaminaCostForJumpingEnabled;
    public int StaminaCostForJumping;

    public boolean StaminaCostForSwimmingEnabled;
    public int StaminaCostForSwimming;


    public void load(FileConfiguration config) {
        this.IsPluginEnabled = config.getBoolean("IsPluginEnabled");

        this.StaminaCostForSprintingEnabled = config.getBoolean("StaminaCostForSprintingEnabled");
        this.StaminaCostForSprinting = config.getInt("StaminaCostForSprinting");

        this.StaminaCostForJumpingEnabled = config.getBoolean("StaminaCostForJumpingEnabled");
        this.StaminaCostForJumping = config.getInt("StaminaCostForJumping");

        this.StaminaCostForSwimmingEnabled = config.getBoolean("StaminaCostForSwimmingEnabled");
        this.StaminaCostForSwimming = config.getInt("StaminaCostForSwimming");
    }

    public static Config get() {
        return HungerIsStamina.plugin.config;
    }
}
