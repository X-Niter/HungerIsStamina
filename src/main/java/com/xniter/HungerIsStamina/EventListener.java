package com.xniter.HungerIsStamina;

import io.lumine.mythic.utils.terminable.Terminable;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class EventListener implements Listener {
    public EventListener() {
    }

    @EventHandler
    private void foodEvent(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(
            ignoreCancelled = true
    )
    private void damage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            e.setCancelled(true);
        }

    }

    @EventHandler(
            ignoreCancelled = true
    )
    private void sprintStamina(PlayerResourceUpdateEvent e) {
        final Player p = e.getPlayer();
        //final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(e.getData().getPlayer());

        if (e.getResource() == PlayerResource.STAMINA) {

            if (Config.get().StaminaCostForSprintingEnabled) {
                if (e.getPlayer().isSprinting()) {
                    p.setFoodLevel(this.calc(e.getData()));
                }
            }

            if (Config.get().StaminaCostForJumpingEnabled) {
                if (e.getPlayer().isJumping()) {
                    p.setFoodLevel(this.calc(e.getData()));
                }
            }

            if (Config.get().StaminaCostForSwimmingEnabled) {
                if (e.getPlayer().isSwimming()) {
                    p.setFoodLevel(this.calc(e.getData()));
                }
            }

            p.setFoodLevel(this.calc(e.getData()));
        }
    }

    @EventHandler
    private void runEvent(PlayerToggleSprintEvent e) {
        final Player p = e.getPlayer();
        final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(e.getPlayer().getUniqueId());

        if (Config.get().StaminaCostForSprintingEnabled) {
            if (e.getPlayer().isSprinting()) {
                if (Config.get().StaminaCostForSprinting >= 1) {
                    data.setStamina(data.getStamina() - Config.get().StaminaCostForSprinting);
                }
            }
        }

        if (Config.get().StaminaCostForJumpingEnabled) {
            if (e.getPlayer().isJumping()) {
                if (Config.get().StaminaCostForJumping >= 1) {
                    data.setStamina(data.getStamina() - Config.get().StaminaCostForJumping);
                }
            }
        }

        if (Config.get().StaminaCostForSwimmingEnabled) {
            if (e.getPlayer().isSwimming()) {
                if (Config.get().StaminaCostForSwimming >= 1) {
                    data.setStamina(data.getStamina() - Config.get().StaminaCostForJumping);
                }
            }
        }
    }

    private int calc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(StatType.MAX_STAMINA) * 20.0D)));
    }
}
