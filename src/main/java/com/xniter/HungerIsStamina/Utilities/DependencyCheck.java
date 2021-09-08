package com.xniter.HungerIsStamina.Utilities;

import com.xniter.HungerIsStamina.HungerIsStamina;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class DependencyCheck implements Listener {

    private final HungerIsStamina plugin;

    public DependencyCheck() {
        this.plugin = HungerIsStamina.getInstance();
    }

    @EventHandler
    public void onEnable(final PluginEnableEvent event) {
        plugin.checkDependencies();
    }

}
