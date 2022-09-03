package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.Events.JumpChecker;
import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.configuration.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class Regeneration implements Listener {
    public static HungerIsStamina main;

    public Regeneration(HungerIsStamina his) {
        main = his;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();

            for (Player p : online_players) {
                if (p != null && p.isOnline() && main.getConfig().getBoolean("EnableRegen", true)) {
                    if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                        //final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                        if (!p.isSprinting() && !JumpChecker.isJumping() && !p.isSwimming() && !p.isDead() && !p.isInvulnerable() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && p.getFoodLevel() != 20) {
                            if (p.getFoodLevel() != main.getConfig().getInt("HungerRegenLimit", 10)) {
                                p.setFoodLevel(p.getFoodLevel() + main.getConfig().getInt("HungerRegenAmount", 1));
                            }
                        }
                    }
                }
            }
        }, 0L, main.getConfig().getInt("RegenTimeInTicks", 80));

        if (main.getConfig().getBoolean("SimpleStamina", false) && main.getConfig().getBoolean("EnableRegen", true)) {
             main.consoleOutput.warn(Message.ENABLE_REGEN_CONFLICT.get());
        }
    }
}
