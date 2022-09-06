package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static io.lumine.mythic.lib.api.stat.SharedStat.MAX_STAMINA;

public class ResourceUpdate implements Listener {

    HungerIsStamina main;

    public ResourceUpdate(HungerIsStamina his) {
        main = his;
    }

    @EventHandler
    private void resourceStamina(PlayerResourceUpdateEvent e) {
        final Player p = e.getPlayer();
        final PlayerData data = MMOCore.plugin.dataProvider.getDataManager().get(e.getData().getPlayer());

        if (e.getResource() == PlayerResource.STAMINA) {
            if (main.getConfig().getBoolean("SimpleStamina", false)) {
                if (staminaToFoodCalc(data) != p.getFoodLevel()) {
                    p.setFoodLevel(staminaToFoodCalc(data));
                }
            }
            if (main.getConfig().getBoolean("SprintRegenDisable", true)) {
                if (p.isSprinting()) {
                    e.setCancelled(true);
                }
            }
            if (!main.getConfig().getBoolean("SimpleStamina", false)) {
                if (data.getStamina() <= 0.3 && p.getFoodLevel() < main.getConfig().getInt("HungerRegenLimit", 10) || p.getFoodLevel() < main.getConfig().getInt("HungerRegenLimit", 10)) {
                    e.setCancelled(true);
                }
            }
            if (main.getConfig().getBoolean("JumpRegenDisable", true)) {
                if (JumpChecker.isJumping()) {
                    e.setCancelled(true);
                }
            }
            if (main.getConfig().getBoolean("SwimRegenDisable", true)) {
                if (p.isSwimming()) {
                    e.setCancelled(true);
                }
            }
            if (main.getConfig().getBoolean("DisableStaminaRegen", false)) {
                e.setCancelled(true);
            }

        }
    }

    public int staminaToFoodCalc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(MAX_STAMINA) * 20)));
    }
}
