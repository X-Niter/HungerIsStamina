package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.configuration.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final HungerIsStamina plugin;

    public PlayerJoin(HungerIsStamina instance) {
        this.plugin = instance;
    }

    // Inform about a new version
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // newVersion will be null when the checker is disabled, or there are no new available
        if (!player.hasPermission("hungerisstamina.admin") || plugin.newVersion == null) return;

        player.sendMessage(Message.UPDATE.get(player)
                .replaceAll("(?i)%newVersion%", plugin.newVersion)
                .replaceAll("(?i)%version%", plugin.getDescription().getVersion()));
    }
}
