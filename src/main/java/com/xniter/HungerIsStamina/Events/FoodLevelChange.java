package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChange implements Listener {

    HungerIsStamina main;

    public FoodLevelChange(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void foodLevelChange(FoodLevelChangeEvent e) {
        /*if (main.getConfig().getBoolean("StaminaCostForSprintingEnabled", true) || main.getConfig().getBoolean("StaminaCostForJumpingEnabled", true) || main.getConfig().getBoolean("StaminaCostForSwimmingEnabled", true)) {
            Player p = ((Player) e.getEntity());
            if (e.getEntity() instanceof Player) {
                if (p.isSprinting() || p.isSwimming() || p.isJumping()) {
                    e.setCancelled(true);
                }
            }
        }*/
        e.setCancelled(true);
    }

}
