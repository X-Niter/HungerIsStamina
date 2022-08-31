package com.xniter.HungerIsStamina.Listeners;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.IJumping;
import com.xniter.HungerIsStamina.Utilities.Utils;
import com.xniter.HungerIsStamina.configuration.Message;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Iterator;

public class Regeneration implements Listener {
    public static HungerIsStamina main;

    private static IJumping iJumping;

    public Regeneration(HungerIsStamina his) {
        main = his;

        // Default Regeneration

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            Collection<? extends Player> online_players = Bukkit.getOnlinePlayers();
            Iterator var3 = online_players.iterator();

            while (var3.hasNext()) {
                Player p = (Player) var3.next();
                if (p != null && p.isOnline() && main.getConfig().getBoolean("EnableRegen", true)) {
                    if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                        //final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(p.getPlayer().getUniqueId());
                        if (!p.isSprinting() && !iJumping.isJumping() && !p.isSwimming() && !p.isDead() && !p.isInvulnerable() && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && p.getFoodLevel() != 20) {
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
