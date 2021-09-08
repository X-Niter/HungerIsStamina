package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import org.bukkit.entity.Player;
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
        Player p = ((Player) e.getEntity());

        if (main.getConfig().getBoolean("StaminaCostForSprintingEnabled", true)) {
            if (p.isSprinting()) {
                e.setCancelled(true);
            }
        }
        if (main.getConfig().getBoolean("StaminaCostForJumpingEnabled", true)) {
            if (p.isJumping()) {
                e.setCancelled(true);
            }
        }
        if (main.getConfig().getBoolean("StaminaCostForSwimmingEnabled", true)) {
            if (p.isSwimming()) {
                e.setCancelled(true);
            }
        }
    }
}
