package com.xniter.HungerIsStamina.Events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Listeners.PlayerJumping;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResourceUpdate implements Listener {

    HungerIsStamina main;

    public ResourceUpdate(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void resourceStamina(PlayerResourceUpdateEvent e) {
        final Player p = e.getPlayer();
        final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(e.getData().getPlayer());

        if (e.getResource() == PlayerResource.STAMINA) {
            if (data.getStamina() <= 0.3 && p.getFoodLevel() <= 15) {
                data.setStamina(0);
                e.setCancelled(true);
            }
            if (main.getConfig().getBoolean("SprintRegenDisable", true)) {
                if (p.isSprinting()) {
                    e.setCancelled(true);
                }
            }
            if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                if (p.getFoodLevel() < main.getConfig().getInt("HungerRegenLimit", 10)) {
                    e.setCancelled(true);
                }
            }
            if (main.getConfig().getBoolean("JumpRegenDisable", true)) {
                if (p.isJumping()) {
                    e.setCancelled(true);
                }
            }
            if (main.getConfig().getBoolean("SwimRegenDisable", true)) {
                if (p.isSwimming()) {
                    e.setCancelled(true);
                }
            }
            if (main.getConfig().getBoolean("DisableStaminaRegen", false)) {
                e.setCancelled(true);
            }

        }
    }
}
