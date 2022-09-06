package com.xniter.HungerIsStamina.commands;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.Utils;
import com.xniter.HungerIsStamina.configuration.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor, Listener {

    private final HungerIsStamina plugin;

    public Commands(HungerIsStamina plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.color("&8&m        &r &3HungerIsStamina &f" + plugin.getDescription().getVersion() + " &8&m        &r"
                    + "\n&3/" + label + "&b Reload &8- &6Reload the plugin."
                    + "\n&3/" + label + "&b Load &8(&bMode&8) - &6Load Plugin Mode."
                    + "\n&3/" + label + "&b Load &8(&bMode&8) (&bEasy&8/&bNormal&8/&bHard&8) - &6Load Plugin Mode Difficulty"
                    + "\n&3/" + label + "&b Debug &8(&ball&8) - &7Enable player debug channel."));
            return true;
        }

        Player player;

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                if (!sender.hasPermission("hungerisstamina.reload")) {
                    sender.sendMessage(Message.NO_PERM.get());
                    return true;
                }
                plugin.reload(sender);
            }
            case "debug" -> {
                if (!sender.hasPermission("hungerisstamina.debug")) {
                    sender.sendMessage(Message.NO_PERM.get());
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Message.ONLY_PLAYERS.get());
                    return true;
                }
                player = (Player) sender;
                if (args.length > 1 && args[1].equalsIgnoreCase("all")) {
                    if (plugin.getConsoleOutput().getListeners().contains(sender)) {
                        plugin.getConsoleOutput().removeListener(sender);
                        sender.sendMessage(Utils.color(Message.PREFIX.getValue() + " &cYou are no longer listening."));
                    } else {
                        plugin.getConsoleOutput().addListener(sender);
                        sender.sendMessage(Utils.color(Message.PREFIX.getValue() + " &aYou are listening to everything."));
                    }
                    return false;
                }
                if (plugin.getConsoleOutput().switchPersonalDebug(sender)) {
                    sender.sendMessage(Message.DEBUG_ON.get(player));
                } else {
                    sender.sendMessage(Message.DEBUG_OFF.get(player));
                }
            }
            /*case "load":
                if (!sender.hasPermission("hungerisstamina.load.simplestamina") || !sender.hasPermission("hungerisstamina.load.advancedstamina")) {
                    sender.sendMessage(Message.NO_PERM.get());
                    return true;
                }
                player = (Player) sender;

                if (args.length > 1 && args[1].equalsIgnoreCase("simplestamina")) {

                    plugin.getConfig().set("SimpleStamina", true);
                    plugin.getConfig().set("StaminaCostForSprintingEnabled", true);
                    plugin.getConfig().set("StaminaCostForSprinting", 1);
                    plugin.getConfig().set("StaminaDrainTickSpeedSprint", 40);
                    plugin.getConfig().set("SprintRegenDisable", true);
                    plugin.getConfig().set("StaminaCostForJumpingEnabled", true);
                    plugin.getConfig().set("StaminaCostForJumping", 3);
                    plugin.getConfig().set("StaminaDrainTickSpeedJump", 5);
                    plugin.getConfig().set("JumpRegenDisable", true);
                    plugin.getConfig().set("StaminaCostForSwimmingEnabled", true);
                    plugin.getConfig().set("StaminaCostForSwimming", 1);
                    plugin.getConfig().set("StaminaDrainTickSpeedSwim", 40);
                    plugin.getConfig().set("SwimRegenDisable", false);
                    plugin.getConfig().set("StarvationDamage", true);
                    plugin.getConfig().set("StarvationDamageValue", 1);
                    plugin.getConfig().set("EnableRegen", false);
                    plugin.getConfig().set("HungerRegenLimit", 0);
                    plugin.getConfig().set("RegenTimeInTicks", 0);
                    plugin.getConfig().set("FoodFillStaminaPartial", false);
                    plugin.getConfig().set("EnableCustomFoodValues", true);
                    plugin.getConfig().set("Update-Checker", true);
                    plugin.getConfig().set("IsPluginEnabled", true);
                    plugin.getConfig().set("Debug-Enabled", false);
                    plugin.getConfig().set("DisableStaminaRegen", false);
                    sender.sendMessage(Message.SIMPLE_STAMINA_MODE.get(player));
                    plugin.reload(Bukkit.getConsoleSender());
                    sender.sendMessage(Message.SIMPLE_STAMINA_MODE_ACTIVATED.get(player));
                    return true;
                }

                if (args.length > 1 && args[1].equalsIgnoreCase("advancedstamina")) {
                    plugin.getConfig().set("SimpleStamina", false);
                    plugin.getConfig().set("StaminaCostForSprintingEnabled", true);
                    plugin.getConfig().set("StaminaCostForSprinting", 1);
                    plugin.getConfig().set("StaminaDrainTickSpeedSprint", 40);
                    plugin.getConfig().set("SprintRegenDisable", true);
                    plugin.getConfig().set("StaminaCostForJumpingEnabled", true);
                    plugin.getConfig().set("StaminaCostForJumping", 2);
                    plugin.getConfig().set("StaminaDrainTickSpeedJump", 6);
                    plugin.getConfig().set("JumpRegenDisable", true);
                    plugin.getConfig().set("StaminaCostForSwimmingEnabled", true);
                    plugin.getConfig().set("StaminaCostForSwimming", 1);
                    plugin.getConfig().set("StaminaDrainTickSpeedSwim", 40);
                    plugin.getConfig().set("SwimRegenDisable", true);
                    plugin.getConfig().set("StarvationDamage", true);
                    plugin.getConfig().set("StarvationDamageValue", 1);
                    plugin.getConfig().set("EnableRegen", true);
                    plugin.getConfig().set("HungerRegenLimit", 10);
                    plugin.getConfig().set("RegenTimeInTicks", 80);
                    plugin.getConfig().set("FoodFillStaminaPartial", true);
                    plugin.getConfig().set("EnableCustomFoodValues", true);
                    plugin.getConfig().set("Update-Checker", true);
                    plugin.getConfig().set("IsPluginEnabled", true);
                    plugin.getConfig().set("Debug-Enabled", false);
                    plugin.getConfig().set("DisableStaminaRegen", false);
                    sender.sendMessage(Message.ADVANCED_STAMINA_MODE.get(player));
                    plugin.reload(Bukkit.getConsoleSender());
                    sender.sendMessage(Message.ADVANCED_STAMINA_MODE_ACTIVATED.get(player));

                    break;
                }*/
        }
        return true;
    }
}
