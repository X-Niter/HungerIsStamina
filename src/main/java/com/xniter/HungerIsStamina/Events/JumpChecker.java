package com.xniter.HungerIsStamina.Events;

import com.google.common.collect.Sets;
import com.xniter.HungerIsStamina.HungerIsStamina;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Set;
import java.util.UUID;

public class JumpChecker implements Listener {

    HungerIsStamina main;

    private static boolean jumping = false;

    public JumpChecker(HungerIsStamina his) {
        main = his;
    }

    private final Set<UUID> prevPlayersOnGround = Sets.newHashSet();

    @EventHandler
    public void setJumpStatus(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = (double) 0.42F;
            double jumpBoostOneVelocity = (double) 0.52F;
            double jumpBoostTwoVelocity = (double) 0.62F;

            if (e.getPlayer().getLocation().getBlock().getType() != Material.WATER && e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                if (!player.isOnGround() && !player.isFlying() && !player.isInvulnerable() && !player.isInWater() && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR && !player.isFlying()) {
                    if (Double.compare(player.getVelocity().getY(), jumpVelocity) == 0 || Double.compare(player.getVelocity().getY(), jumpBoostOneVelocity) == 0 || Double.compare(player.getVelocity().getY(), jumpBoostTwoVelocity) == 0) {
                        //player.setJumping(true);
                        setJumping(true);
                        if (!isJumping()) {
                            jumping = true;
                        }
                    }
                }
            }
        }
        if (player.isOnGround()) {
            //player.setJumping(false);
            setJumping(false);
            if (isJumping()) {
                jumping = false;
            }
            prevPlayersOnGround.add(player.getUniqueId());
        } else {
            prevPlayersOnGround.remove(player.getUniqueId());
        }
    }

    public static boolean isJumping() {
        return jumping;
    }

    public static void setJumping(boolean isJumping) {
        jumping = isJumping;
    }
}
