package com.xniter.HungerIsStamina.Listeners;

import com.google.common.collect.Sets;
import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.IJumping;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import static io.lumine.mythic.lib.api.stat.SharedStat.MAX_STAMINA;

public class PlayerJumping implements Listener {
    public static HungerIsStamina main;

    private static IJumping iJumping;

    public PlayerJumping(HungerIsStamina his) {
        main = his;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while (var3.hasNext()) {
                Player p = (Player) var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForJumpingEnabled", true)) {
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    //Vector velocity = p.getVelocity();
                    if (!p.isDead() && !p.isInWater() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        // Default Advanced System
                        if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (iJumping.isJumping() && !p.isInvulnerable()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForJumping", 1));
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForJumping", 1));
                                }
                            }
                            // Sneaking and Jumping
                            if (p.isSneaking() && iJumping.isJumping() && !p.isInvulnerable()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForJumping", 1) - 0.5);
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForJumping", 1));
                                }
                            }
                        }

                        // Simple Stamina System
                        if (main.getConfig().getBoolean("SimpleStamina", false)) {
                            // Jumping
                            if (iJumping.isJumping() && !p.isInvulnerable()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForJumping", 1));
                                p.setFoodLevel(staminaToFoodCalc(data));
                            }
                            // Sneaking and Jumping
                            if (p.isSneaking() && iJumping.isJumping() && !p.isInvulnerable()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForJumping", 1) - 0.5);
                                p.setFoodLevel(staminaToFoodCalc(data));
                            }
                        }
                    }
                }
            }
        }, 0, (int) main.getConfig().get("StaminaDrainTickSpeedJump", 20));

    }

    public int staminaToFoodCalc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(MAX_STAMINA) * 20.0D)));
    }
}
