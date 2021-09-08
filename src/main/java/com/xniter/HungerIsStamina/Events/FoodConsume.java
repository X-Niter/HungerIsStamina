package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.DefaultFood;
import com.xniter.HungerIsStamina.configuration.Files;
import com.xniter.HungerIsStamina.configuration.Message;
import lombok.NonNull;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Objects;
import java.util.Random;

public class FoodConsume implements Listener {

    HungerIsStamina main;

    public FoodConsume(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void foodConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        String item = e.getItem().getType().name().toLowerCase();
        PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(Objects.requireNonNull(p.getPlayer()).getUniqueId());
        FileConfiguration config = main.getConfig();
        FileConfiguration foodConfig = main.getFoodConfig();
        Random random = new java.util.Random();
        int rng = random.nextInt(2) + 1;



        if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && !p.isInvulnerable()) {
            if (!e.getItem().getType().equals(Material.POTION)) {
                if (config.getBoolean("EnableCustomFoodValues", true)) {
                    if (foodConfig.getString(item) != null) {
                        e.setCancelled(true);
                        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                        if (!config.getBoolean("SimpleStamina", false)) {
                            p.setFoodLevel((int) Math.min(p.getFoodLevel() + foodConfig.getDouble(item + ".food"), 20));
                            if (config.getBoolean("FoodFillStaminaPartial", true)) {
                                data.setStamina((int) Math.min(data.getStamina() + foodConfig.getDouble(item + ".food") / rng + rng - rng, 20));
                            }
                        } else {
                            data.setStamina(data.getStamina() + foodConfig.getDouble(item + ".food"));
                            p.setFoodLevel(staminaToFoodCalc(data));
                        }
                        if (foodConfig.getDouble(item + ".damage") != 0) {
                            p.damage(foodConfig.getDouble(item + ".damage"));
                        }
                    }
                }
                else if (DefaultFood.valueOfName(item) != null) {
                    e.setCancelled(true);
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    if (!config.getBoolean("SimpleStamina", false)) {
                        p.setFoodLevel(p.getFoodLevel() + DefaultFood.valueOfName(item).hungerValue());
                        if (config.getBoolean("FoodFillStaminaPartial", true)) {
                            data.setStamina((int) Math.min(data.getStamina() + (float)DefaultFood.valueOfName(item).hungerValue() / rng + rng - rng, 20));
                        }
                    } else {
                        data.setStamina(data.getStamina() + (float)DefaultFood.valueOfName(item).hungerValue() / rng + rng - rng);
                        p.setFoodLevel(staminaToFoodCalc(data));
                    }
                    if (DefaultFood.valueOfName(item).damageValue() != 0) {
                        p.damage(DefaultFood.valueOfName(item).damageValue());
                    }
                }
            }
        }
    }

    public int staminaToFoodCalc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(StatType.MAX_STAMINA) * 20.0D)));
    }
}
