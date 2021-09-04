package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener {

    HungerIsStamina main;

    public Damage(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void damage(EntityDamageEvent e) {
        if (main.getConfig().getBoolean( "StarvationDamage", false)) {
            if (main.getConfig().getDouble("StarvationDamageValue", 0.5) <= 0.0) {
                main.consoleOutput.warn("Starvation Damage is invalid, auto Disabling StarvationDamage.");
                main.getConfig().set("StarvationDamage", false);
                main.reload(Bukkit.getConsoleSender());
            } else {
                if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                    e.setDamage(main.getConfig().getDouble("StarvationDamageValue", 0.5));
                }
            }
        }

        if (!main.getConfig().getBoolean( "StarvationDamage", false)) {
            if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                e.setCancelled(true);
            }
        }


    }
}
