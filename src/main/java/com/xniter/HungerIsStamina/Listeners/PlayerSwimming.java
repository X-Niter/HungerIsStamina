package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.HungerIsStamina;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Iterator;

public class PlayerSwimming implements Listener {
    public static HungerIsStamina main;

    public PlayerSwimming(HungerIsStamina his) {
        main = his;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while (var3.hasNext()) {
                Player p = (Player) var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("StaminaCostForSwimmingEnabled", true)) {
                    final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                    if (p.isInWater() && !p.isDead() && !p.isSprinting() && !p.isInvulnerable() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {

                        // Default System
                        if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (p.isSwimming()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSwimming", 1));
                                if (data.getStamina() <= 1) {
                                    data.setStamina(0);
                                    p.setFoodLevel(p.getFoodLevel() - main.getConfig().getInt("StaminaCostForSwimming", 1));
                                }
                            }
                        }

                        // Simple Stamina
                        if (main.getConfig().getBoolean("SimpleStamina", false)) {
                            if (p.isSwimming()) {
                                data.setStamina(data.getStamina() - main.getConfig().getInt("StaminaCostForSwimming", 1));
                                p.setFoodLevel(staminaToFoodCalc(data));
                            }
                        }
                    }
                }
            }
        }, 0, (int) main.getConfig().get("StaminaDrainTickSpeedSwim", 20));
    }

    public int staminaToFoodCalc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(StatType.MAX_STAMINA) * 20.0D)));
    }
}
