package com.xniter.HungerIsStamina;

import com.xniter.HungerIsStamina.Events.*;
import com.xniter.HungerIsStamina.Listeners.*;
import com.xniter.HungerIsStamina.PlaceholderAPI.PlaceholderInit;
import com.xniter.HungerIsStamina.Utilities.ConsoleOutput;
import com.xniter.HungerIsStamina.Utilities.MetricsLite;
import com.xniter.HungerIsStamina.Utilities.UpdateChecker;
import com.xniter.HungerIsStamina.Utilities.Utils;
import com.xniter.HungerIsStamina.commands.Commands;
import com.xniter.HungerIsStamina.configuration.Files;
import com.xniter.HungerIsStamina.configuration.Foods;
import com.xniter.HungerIsStamina.configuration.Message;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HungerIsStamina extends JavaPlugin {
    @Getter
    public static HungerIsStamina instance;

    @Getter
    private boolean usePlaceholderAPI = false;

    @Getter
    private Files files;

    @Getter
    public ConsoleOutput consoleOutput;

    public String newVersion = null;

    public void onEnable() {
        instance = this;


        saveDefaultConfig();


        files = new Files();

        consoleOutput = new ConsoleOutput(this);
        consoleOutput.setColors(true);

        //files.getConfig().load();

        Message.load();


        Foods.load();
        getFoodConfig().options().copyDefaults(true);
        files.getFoods().save();


        // Some Debug Stuff
        consoleOutput.setDebug(files.getConfig().getFileConfiguration().getBoolean("Debug-Enabled", false));
        consoleOutput.setPrefix(Objects.requireNonNull(Utils.color(Message.PREFIX.getValue())));


        // Register all of your Events/Listeners
        // With out them, this plugin isn't really of any function
        registerListeners();


        // Pretty sure this can get cleaned up
        // This runs a check to make sure we have our dependencies available
        checkDependencies();


        // Register plugin commands
        getCommand("his").setExecutor(new Commands(this));


        // Notify op/admin/owner if this plugin has an update on spigot website
        if (getConfig().getBoolean("Update-Checker", false)) {
            getServer().getScheduler().runTaskLaterAsynchronously(this, () -> {
                UpdateChecker updater = new UpdateChecker(this, 95964);
                try {
                    if (updater.checkForUpdates()) {
                        newVersion = updater.getLatestVersion();
                    }
                } catch (Exception e) {
                    consoleOutput.warn("Could not check for updates.");
                }
            }, 20L);
        }

        // Config version checking to help notify server admins the config is outdated
        final int configVersion = getConfig().contains("config-version", true) ? getConfig().getInt("config-version") : -1;
        final int defConfigVersion = 2;
        if(configVersion != defConfigVersion) {
            getLogger().warning("You may be using an outdated config.yml!");
            getLogger().warning("(Your config version: '" + configVersion + "' | Expected config version: '" + defConfigVersion + "')");
        }


        // Checks if this plugin was set to disabled in the configs
        if (!getConfig().getBoolean("isPluginEnabled", true)) {
            consoleOutput.err("&4PLUGIN DISABLED, SHUTTING DOWN!!");
            Bukkit.getPluginManager().disablePlugin(this);
        }


        // Metrics by Labeled "HungerIsStamina"
        enableMetrics();
    }

    private void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new Commands(this), this);
        pluginManager.registerEvents(new Damage(this), this);
        pluginManager.registerEvents(new FoodConsume(this), this);
        pluginManager.registerEvents(new FoodLevelChange(this), this);
        pluginManager.registerEvents(new JumpChecker(this), this);
        pluginManager.registerEvents(new ResourceUpdate(this), this);
        pluginManager.registerEvents(new PlayerJoin(this), this);
        pluginManager.registerEvents(new PlayerJumping(this), this);
        pluginManager.registerEvents(new PlayerSprinting(this), this);
        pluginManager.registerEvents(new PlayerSwimming(this), this);
        pluginManager.registerEvents(new Regeneration(this), this);
    }

    public void reload(CommandSender sender) {

        Utils.events.clear();

        checkDependencies();

        files.getConfig().load();
        consoleOutput.setDebug(files.getConfig().getFileConfiguration().getBoolean("Debug-Enabled", false));

        Message.load();

        Foods.load();

        consoleOutput.setPrefix(Utils.color(Message.PREFIX.getValue()));

        sender.sendMessage(Message.RELOAD.get());
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getPluginManager().disablePlugin(this);
    }

    public void checkDependencies() {
        setupMMOCore();
        setupPlaceholderAPI();
    }

    private void setupMMOCore() {
        if (!getServer().getPluginManager().isPluginEnabled("MMOCore")) {
            consoleOutput.err("&4&lYOU ARE MISSING PLUGIN -> MMOCore! DISABLING HungerIsStamina");
            Bukkit.getPluginManager().disablePlugin(this);
        } else
            consoleOutput.info("MMOCore plugin found! &aEnabling HungerIsStamina plugin!");
    }

    private void setupPlaceholderAPI() {
        if (!getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            consoleOutput.err("&4&lYOU ARE MISSING PLUGIN -> PlaceholderAPI! DISABLING HungerIsStamina");
            Bukkit.getPluginManager().disablePlugin(this);
        } else
            consoleOutput.info("&aInitializing placeholders");
            new PlaceholderInit(this).register();
    }

    public void enableMetrics() {
        new MetricsLite(this);
        consoleOutput.info("&8MetricsLite enabled");
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return files.getConfig().getFileConfiguration();
    }


    public @NotNull FileConfiguration getFoodConfig() {
        return files.getFoods().getFileConfiguration();
    }
}
