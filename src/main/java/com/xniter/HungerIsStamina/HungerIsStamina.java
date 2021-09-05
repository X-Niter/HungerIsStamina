package com.xniter.HungerIsStamina;

import com.xniter.HungerIsStamina.Events.*;
import com.xniter.HungerIsStamina.Listeners.Regeneration;
import com.xniter.HungerIsStamina.Listeners.PlayerJoin;
import com.xniter.HungerIsStamina.PlaceholderAPI.PlaceholderInit;
import com.xniter.HungerIsStamina.Utilities.*;
import com.xniter.HungerIsStamina.commands.Commands;
import com.xniter.HungerIsStamina.configuration.Foods;
import com.xniter.HungerIsStamina.configuration.Message;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import com.xniter.HungerIsStamina.configuration.Files;

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

        files = new Files();

        consoleOutput = new ConsoleOutput(this);
        consoleOutput.setColors(true);

        files.getSettings().load();
        Message.load();

        Foods.load();
        getFoodConfig().options().copyDefaults(true);
        files.getFoods().save();

        consoleOutput.setDebug(files.getSettings().getFileConfiguration().getBoolean("Debug-Enabled", false));
        consoleOutput.setPrefix(Objects.requireNonNull(Utils.color(Message.PREFIX.getValue())));

        registerListeners();

        checkDependencies();

        getCommand("his").setExecutor(new Commands(this));

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

        final int configVersion = getConfig().contains("config-version", true) ? getConfig().getInt("config-version") : -1;
        final int defConfigVersion = 5;
        if(configVersion != defConfigVersion) {
            getLogger().warning("You may be using an outdated config.yml!");
            getLogger().warning("(Your config version: '" + configVersion + "' | Expected config version: '" + defConfigVersion + "')");
        }

        if (!getConfig().getBoolean("isPluginEnabled", true)) {
            consoleOutput.err("&4PLUGIN DISABLED, SHUTTING DOWN!!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        int pluginId = 12704;
        Metrics metrics = new Metrics(this, pluginId);
        consoleOutput.info("&8MetricsLite enabled");
    }

    private void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new Commands(this), this);
        pluginManager.registerEvents(new Damage(this), this);
        pluginManager.registerEvents(new FoodConsume(this), this);
        pluginManager.registerEvents(new FoodLevelChange(this), this);
        pluginManager.registerEvents(new ResourceUpdate(this), this);
        pluginManager.registerEvents(new PlayerJoin(this), this);
        pluginManager.registerEvents(new Regeneration(this), this);
    }

    public void reload(CommandSender sender) {

        Utils.events.clear();

        checkDependencies();

        files.getSettings().load();
        consoleOutput.setDebug(files.getSettings().getFileConfiguration().getBoolean("Debug-Enabled", false));

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
            consoleOutput.err("&4&lYOU ARE MISSING PLUGIN -> MMOCORE!");
            Bukkit.getPluginManager().disablePlugin(this);
        } else
            consoleOutput.info("MMOCore plugin found! &aEnabling HungerIsStamina plugin!");
    }

    private void setupPlaceholderAPI() {
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") && !usePlaceholderAPI) {
            usePlaceholderAPI = true;
            consoleOutput.info("Found PlaceholderAPI! &aUsing it for placeholders.");
            new PlaceholderInit(this).register();
        }
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return files.getSettings().getFileConfiguration();
    }


    public @NotNull FileConfiguration getFoodConfig() {
        return files.getFoods().getFileConfiguration();
    }
}
