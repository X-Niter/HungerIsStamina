package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.Events.JumpChecker;
import com.xniter.HungerIsStamina.HungerIsStamina;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.joml.Math;

import java.util.Collection;
import java.util.Objects;

import static io.lumine.mythic.lib.api.stat.SharedStat.MAX_STAMINA;

public class PlayerSprinting implements Listener {
    public static HungerIsStamina main;

    public PlayerSprinting(HungerIsStamina his) {
        main = his;

        // Default Sprinting Listener

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();

            for (Player p : online_players) {
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForSprintingEnabled", true)) {
                    //int foodReduceAmount = Utils.getFoodReduceAmount(p);
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(Objects.requireNonNull(p.getPlayer()).getUniqueId());
                    if (!p.isDead() && !p.isFlying() && !p.isSwimming() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && !p.isInvulnerable()) {
                        // Run & Jumping
                        if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (p.isSprinting() && JumpChecker.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1) - main.getConfig().getInt("StaminaCostForJumping", 2));
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    //TODO Option to disable it taking away hunger; which would rule out the hunger completely
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSprinting", 1) - 1);
                                }
                            }
                            // Running and not Jumping
                            if (p.isSprinting() && !JumpChecker.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    //TODO Option to disable it taking away hunger; which would rule out the hunger completely
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                                }
                            }
                        }
                        if (main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (p.isSprinting() && JumpChecker.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1) - main.getConfig().getInt("StaminaCostForJumping", 2));
                                p.setFoodLevel(staminaToFoodCalc(data));
                            }
                            // Running and not Jumping
                            if (p.isSprinting() && !JumpChecker.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                                p.setFoodLevel(staminaToFoodCalc(data));
                            }
                        }
                    }
                }
            }

        }, 0, (int) main.getConfig().get("StaminaDrainTickSpeedSprint", 20));
    }
    public int staminaToFoodCalc(PlayerData data) {
        return (int) Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(MAX_STAMINA) * 20.0D)));
    }
}