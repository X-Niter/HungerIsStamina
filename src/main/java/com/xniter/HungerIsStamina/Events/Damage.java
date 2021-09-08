package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class Damage implements Listener {

    HungerIsStamina main;

    public Damage(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void damage(EntityDamageEvent e) {
        if (!e.getEntity().isInvulnerable()) {
            if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                if (!main.getConfig().getBoolean("StarvationDamage", false)) {
                    e.setCancelled(true);
                }
                else if (main.getConfig().getDouble("StarvationDamageValue", 0.5) <= 0.0) {
                    e.setCancelled(true);
                    main.consoleOutput.err("Starvation Damage is invalid, please set value above 0.0 or disable Starvation Damage.");
                } else {
                    e.setDamage(main.getConfig().getDouble("StarvationDamageValue", 0.5));
                }
            }
        }
    }
}