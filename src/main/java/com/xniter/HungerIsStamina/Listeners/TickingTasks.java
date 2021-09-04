package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.HungerIsStamina;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Iterator;

public class TickingTasks implements Listener {
    public static HungerIsStamina main;

    public TickingTasks(HungerIsStamina his) {
        main = his;
        // Sprinting Tick Task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while(var3.hasNext()) {
                Player p = (Player)var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForSprintingEnabled", true)) {
                    //int foodReduceAmount = Utils.getFoodReduceAmount(p);
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    if (!p.isDead() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        // Run & Jumping
                        if (p.isSprinting() && p.isJumping()) {
                            data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1) - 1.25);
                            if (data.getStamina() <= 1) {
                                data.setStamina(0);
                                p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSprinting", 1) - 1);
                            }
                        }
                        // Running and not Jumping
                        if (p.isSprinting() && !p.isJumping()) {
                            data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                            if (data.getStamina() <= 1) {
                                data.setStamina(0);
                                p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSprinting", 1));
                            }
                        }
                    }
                }
            }

        }, 0, (int)main.getConfig().get("StaminaDrainTickSpeedSprint", 20));

        // Jumping Tick task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while(var3.hasNext()) {
                Player p = (Player)var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForJumpingEnabled", true)) {
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    if (!p.isDead() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        // Jumping
                        if (p.isJumping()) {
                            data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForJumping", 1));
                            if (data.getStamina() <= 1) {
                                data.setStamina(0);
                                p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForJumping", 1));
                            }
                        }
                        // Sneaking and Jumping
                        if (p.isSneaking() && p.isJumping()) {
                            data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForJumping", 1) + 0.5);
                            if (data.getStamina() <= 1) {
                                data.setStamina(0);
                                p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForJumping", 1));
                            }
                        }
                    }
                }
            }

        }, 0, (int)main.getConfig().get("StaminaDrainTickSpeedJump", 20));

        // Swimming Tick
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while(var3.hasNext()) {
                Player p = (Player)var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForSwimmingEnabled", true)) {
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    if (!p.isDead() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        // Swimming
                        if (p.isSwimming()) {
                            data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSwimming", 1));
                            if (data.getStamina() <= 1) {
                                data.setStamina(0);
                                p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSwimming", 1));
                            }
                        }
                    }
                }
            }

        }, 0, (int)main.getConfig().get("StaminaDrainTickSpeedSwim", 20));

        // Hunger & Stamina Regen Controller
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while(var3.hasNext()) {
                Player p = (Player)var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("EnableRegen", true)) {
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    if (!p.isSprinting() && !p.isJumping() && !p.isSwimming() && !p.isDead() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && p.getFoodLevel() != 20) {
                        if (p.getFoodLevel() != main.getConfig().getInt("HungerRegenLimit", 10)) {
                            data.setStamina(0);
                            p.setFoodLevel(p.getFoodLevel() + main.getConfig().getInt("HungerRegenAmount", 1));
                        }
                    }
                }
            }

        }, 0L, main.getConfig().getInt("RegenTimeInTicks", 80));
    }
}
