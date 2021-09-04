package com.xniter.HungerIsStamina.Events;

import com.xniter.HungerIsStamina.HungerIsStamina;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
            if (data.getStamina() == 0 && p.getFoodLevel() <= 15) {
                e.setCancelled(true);
                //data.setStamina(0);
            }
        }
    }
}
