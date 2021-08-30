package com.xniter.HungerIsStamina;

import net.Indyuce.mmocore.api.event.PlayerPostCastSkillEvent;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class EventListener implements Listener {
    public EventListener() {
    }

    @EventHandler(
            ignoreCancelled = true
    )
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
    private void regenStamina(PlayerResourceUpdateEvent e) {
        if (e.getResource() == PlayerResource.STAMINA) {
            e.getPlayer().setFoodLevel(this.calc(e.getData()));
        }
    }

    /*@EventHandler
    private void useStamina(PlayerToggleSprintEvent e) {
        if (e.isSprinting()) {
            e.getPlayer().data);
        }

    }*/

    private int calc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(StatType.MAX_STAMINA) * 20.0D)));
    }
}
