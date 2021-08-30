package com.xniter.HungerIsStamina;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerIsStamina extends JavaPlugin {
    public static HungerIsStamina plugin;
    public final Config config = new Config();

    public HungerIsStamina() {
    }

    public void onLoad() {
        plugin = this;
        this.saveDefaultConfig();
        config.load(getConfig());
    }

    public void onEnable() {
        final int configVersion = getConfig().contains("config-version", true) ? getConfig().getInt("config-version") : -1;
        final int defConfigVersion = getConfig().getDefaults().getInt("config-version");
        if(configVersion != defConfigVersion) {
            getLogger().warning("You may be using an outdated config.yml!");
            getLogger().warning("(Your config version: '" + configVersion + "' | Expected config version: '" + defConfigVersion + "')");
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("MMOCore")) {
            getLogger().severe("YOU ARE MISSING PLUGIN -> MMOCORE!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!Config.get().IsPluginEnabled) {
            getLogger().severe("PLUGIN DISABLED, SHUTTING DOWN!!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
