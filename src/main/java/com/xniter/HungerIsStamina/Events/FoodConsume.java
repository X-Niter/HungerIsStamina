package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodConsume implements Listener {

    HungerIsStamina main;

    public FoodConsume(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void foodConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        // Set string to the name of consumed item
        String item = e.getItem().getType().name().toLowerCase();


        if (main.getConfig().getBoolean("EnableCustomFoodValues", true)) {
            // Check if the consumed item is in the Foods.yml
            if (!e.getItem().getType().equals(Material.POTION)) {
                if (main.getFoodConfig().getString(item) != null) {
                    // Cancel the event
                    e.setCancelled(true);

                    // Don't remove item if player is in creative or spectator
                    if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                    // Add food level to player
                    p.setFoodLevel((int) Math.min(p.getFoodLevel() + main.getFoodConfig().getDouble(item + ".food"), 20));
                    // If the damage is not 0 damage the player
                    if (main.getFoodConfig().getDouble(item + ".damage") != 0) {
                        p.damage(main.getFoodConfig().getDouble(item + ".damage"));
                    }
                }
            }
        }


        if (main.getConfig().getBoolean("FoodFillStaminaPartial", false)) {
            final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
            if (!e.getItem().getType().equals(Material.POTION)) {
                if (p.getFoodLevel() <= 19) {
                    // Check if the consumed item is in the DefaultFoodValues class
                    if (main.getFoodConfig().getDefaults().getString(item) != null) {
                        // Cancel the event
                        e.setCancelled(true);

                        // Put the food down to 19 to allow eating
                        p.setFoodLevel(p.getFoodLevel() - 1);

                        // Don't remove item if player is in creative or spectator
                        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                        }
                        data.setStamina((int) Math.min(data.getStamina() + main.getFoodConfig().getDefaults().getDouble(item + ".food"), 20));
                        // Add food level to player
                        p.setFoodLevel((int) Math.min(p.getFoodLevel() + main.getFoodConfig().getDouble(item + ".food"), 20));                        // If the damage is not 0 damage the player
                        if (main.getFoodConfig().getDouble(item + ".damage") != 0) {
                            p.damage(main.getFoodConfig().getDouble(item + ".damage"));
                        }

                    }
                }
            }
        }
    }
}
