package com.xniter.HungerIsStamina.configuration;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.Utils;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    @Getter
    private final String path;

    @Getter
    private FileConfiguration fileConfiguration;

    @Getter
    private File file;

    private final JavaPlugin plugin;

    public ConfigFile(JavaPlugin plugin, String path) {
        this.path = path.contains(".yml") ? path : path + ".yml";
        this.plugin = plugin;

        load();
    }

    public void load() {
        this.file = new File(plugin.getDataFolder(), this.path);

        if (!file.exists()) {
            try {
                HungerIsStamina.getInstance().saveResource(this.path, false);
            } catch (IllegalArgumentException e) {
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    HungerIsStamina.getInstance().getServer().getConsoleSender().sendMessage(Utils.color("&6[&3HungerIsStamina&6] &cCould not create " + this.path));
                    return;
                }
            }

            HungerIsStamina.getInstance().getServer().getConsoleSender().sendMessage(Utils.color("&6[&3HungerIsStamina&6] &aCreated " + this.path));
        }

        HungerIsStamina.getInstance().getServer().getConsoleSender().sendMessage(Utils.color("&6[&3HungerIsStamina&6] &7Loaded " + this.path));

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            HungerIsStamina.getInstance().getServer().getConsoleSender().sendMessage(Utils.color("&6[&3HungerIsStamina&6] &cCould not save " + this.path));
        }
    }
}