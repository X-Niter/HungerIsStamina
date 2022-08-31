package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.IJumping;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Iterator;

import static io.lumine.mythic.lib.api.stat.SharedStat.MAX_STAMINA;

public class PlayerSprinting implements Listener {
    public static HungerIsStamina main;

    private static IJumping iJumping;


    public PlayerSprinting(HungerIsStamina his) {
        main = his;

        // Default Sprinting Listener

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while (var3.hasNext()) {
                Player p = (Player) var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForSprintingEnabled", true)) {
                    //int foodReduceAmount = Utils.getFoodReduceAmount(p);
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    if (!p.isDead() && !p.isSwimming() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && !p.isInvulnerable()) {
                        // Run & Jumping
                        if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (p.isSprinting() && iJumping.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1) - main.getConfig().getInt("StaminaCostForJumping", 2));
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSprinting", 1) - 1);
                                }
                            }
                            // Running and not Jumping
                            if (p.isSprinting() && !iJumping.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                                }
                            }
                        }
                        if (main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (p.isSprinting() && iJumping.isJumping()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1) - main.getConfig().getInt("StaminaCostForJumping", 2));
                                p.setFoodLevel(staminaToFoodCalc(data));
                            }
                            // Running and not Jumping
                            if (p.isSprinting() && !iJumping.isJumping()) {
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
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(MAX_STAMINA) * 20.0D)));
    }
}