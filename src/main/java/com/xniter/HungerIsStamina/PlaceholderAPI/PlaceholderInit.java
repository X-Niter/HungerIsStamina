package com.xniter.HungerIsStamina.PlaceholderAPI;

import com.xniter.HungerIsStamina.Events.JumpChecker;
import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.IJumping;
import com.xniter.HungerIsStamina.configuration.Message;
import io.lumine.mythic.lib.MythicLib;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.lumine.mythic.lib.api.stat.SharedStat.MAX_STAMINA;

public class PlaceholderInit extends PlaceholderExpansion {

    HungerIsStamina main;

    private static IJumping iJumping;

    public PlaceholderInit(HungerIsStamina his) {
        main = his;
    }


    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    @NotNull
    public String getAuthor() {
        return "X_Niter";
    }

    @NotNull
    public String getIdentifier() {
        return "his";
    }

    @NotNull
    public String getVersion() {
        return HungerIsStamina.getInstance().getDescription().getVersion();
    }

    public String onRequest(OfflinePlayer player, String identifier) {
        PlayerData mmoPlayer = PlayerData.get(player);
        Player p = mmoPlayer.getPlayer();
        FileConfiguration config = main.getConfig();

        double ratio;
        double j;
        StringBuilder format;

        /*
        MMOCore does not give options to modify the stamina bar at all! Which for a plugin that gives an RPG system that YOU have to configure and isn't done out of the box, I would have expected this small ability
        , Nonetheless, here is an option to have a custom MMOCore stamina bar!
         */
        if (identifier.equals("custom_stamina_bar")) {
            format = new StringBuilder();
            int customBarLength = config.getInt("StaminaBarLength", 12);
            String customBarString = config.getString("StaminaBarCharacter", "█");
            ratio = customBarLength * mmoPlayer.getStamina() / mmoPlayer.getStats().getStat(MAX_STAMINA);


            if (!config.getBoolean("SimpleStamina", false)) {

                if (!config.getBoolean("AlternatingBarAndValue", true) && config.getBoolean("ConstantBar", false)) {
                    for (j = 1.0D; j < customBarLength + 1; ++j) {
                        format.append(ratio >= j ? MMOCore.plugin.configManager.staminaFull : (ratio >= j - 0.5D ? MMOCore.plugin.configManager.staminaHalf : MMOCore.plugin.configManager.staminaEmpty)).append(customBarString);
                    }
                    return format.toString();
                }
                if (!config.getBoolean("ConstantBar", false) && config.getBoolean("AlternatingBarAndValue", true)) {

                    if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR && !p.isInvulnerable()) {
                        if (config.getBoolean("StaminaCostForSprintingEnabled", true) && !p.isInWater() && p.isSprinting()) {
                                for (j = 1.0D; j < customBarLength + 1; ++j) {
                                    format.append(ratio >= j ? MMOCore.plugin.configManager.staminaFull : (ratio >= j - 0.5D ? MMOCore.plugin.configManager.staminaHalf : MMOCore.plugin.configManager.staminaEmpty)).append(customBarString);
                                }
                            return format.toString();
                        }
                        else if (config.getBoolean("StaminaCostForJumpingEnabled", true) && iJumping.isJumping()) {
                            for (j = 1.0D; j < customBarLength + 1; ++j) {
                                format.append(ratio >= j ? MMOCore.plugin.configManager.staminaFull : (ratio >= j - 0.5D ? MMOCore.plugin.configManager.staminaHalf : MMOCore.plugin.configManager.staminaEmpty)).append(customBarString);
                            }
                            return format.toString();
                        }
                        else if (config.getBoolean("StaminaCostForSwimmingEnabled", true) && p.isInWater() && p.isSwimming()) {
                            for (j = 1.0D; j < customBarLength + 1; ++j) {
                                format.append(ratio >= j ? MMOCore.plugin.configManager.staminaFull : (ratio >= j - 0.5D ? MMOCore.plugin.configManager.staminaHalf : MMOCore.plugin.configManager.staminaEmpty)).append(customBarString);
                            }
                            return format.toString();
                        }
                        else {
                            format.append(config.getString("SpacerForBarPlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    return format.toString();
                }
            }
            return format.toString();
        } else if (identifier.equals("stamina_value")) {
            format = new StringBuilder();

            if (config.getBoolean("SimpleStamina", false)) {
                format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                return format.toString();
            }

            // If Simple Stamina is off
            if (!config.getBoolean("SimpleStamina", false)) {

                // If Alternating the Stamina bar and Stamina Value are true, We'll make sure the Constant option is false so we don't clash with that option.
                if (config.getBoolean("AlternatingBarAndValue", true) && !config.getBoolean("ConstantValue", false)) {
                    /* Below I start running all the possible checks to make sure the alternating is correctly corresponding to what the admin configured for the plugin.
                    Sense stamina cost for sprinting, jumping, or swimming, can be turned on or off, we want to make sure that only the enabled stamina costs will
                    be what causes the alternating to happen.
                    It would be silly to have jump cost disabled and still see a stamina bar pop up when it's not even taking stamina.
                    Same goes for anything else in that scenario, not just jumping.
                    */


                    /*If player is creative, spectator or is Invulnerable, then we don't alternate sense they won't be using stamina anyways.
                     *Alternating is to only show the bar when stamina is being drained by our plugins actions, Sprinting, Jumping, or swimming*/
                    if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.isInvulnerable()) {
                        format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        return format.toString();
                    }
                    // If Sprint & Jump & Swim are enabled
                    else if (config.getBoolean("StaminaCostForSprintingEnabled", true) && config.getBoolean("StaminaCostForJumpingEnabled", true) && config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!p.isSprinting() && !iJumping.isJumping() && !p.isSwimming()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            // This spacer value option is to allow a player to control what is showing when the placeholder is not visible,
                            // This can be blank " ", or if the player need a character here like "|", then they have the control to do so.
                            // I wanted to give many controlling options on how this plugin works, so being able to leave as many option to the config mean more power to the server owner/admin
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    // Sprint Only
                    else if (config.getBoolean("StaminaCostForSprintingEnabled", true) && !config.getBoolean("StaminaCostForJumpingEnabled", true) && !config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!p.isSprinting() || p.isSprinting() && p.isInWater()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    // Sprint & Jump
                    else if (config.getBoolean("StaminaCostForSprintingEnabled", true) && config.getBoolean("StaminaCostForJumpingEnabled", true) && !config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!p.isSprinting() && !iJumping.isJumping()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    // Sprint & Swim
                    else if (config.getBoolean("StaminaCostForSprintingEnabled", true) && !config.getBoolean("StaminaCostForJumpingEnabled", true) && config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!p.isSprinting() && !p.isSwimming()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    // Jump Only
                    else if (!config.getBoolean("StaminaCostForSprintingEnabled", true) && config.getBoolean("StaminaCostForJumpingEnabled", true) && !config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!iJumping.isJumping()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    // Jump & Swim
                    else if (!config.getBoolean("StaminaCostForSprintingEnabled", true) && config.getBoolean("StaminaCostForJumpingEnabled", true) && config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!iJumping.isJumping() && !p.isSwimming()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    // Swim Only
                    else if (!config.getBoolean("StaminaCostForSprintingEnabled", true) && !config.getBoolean("StaminaCostForJumpingEnabled", true) && config.getBoolean("StaminaCostForSwimmingEnabled", true)) {
                        if (!p.isSwimming()) {
                            format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                        } else {
                            format.append(config.getString("SpacerForValuePlaceHolder", "|"));
                        }
                        return format.toString();
                    }
                    return format.toString();
                }
                return format.toString();
            }
            if (!config.getBoolean("AlternatingBarAndValue", true) && config.getBoolean("ConstantValue", false)) {
                format.append(config.getString("StaminaValueCharacter", "⚡")).append(MythicLib.plugin.getMMOConfig().decimal.format(mmoPlayer.getStamina()));
                return format.toString();
            }
            return format.toString();
        }
        return null;
    }
}
