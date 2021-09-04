package com.xniter.HungerIsStamina.commands;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.configuration.Message;
import com.xniter.HungerIsStamina.Utilities.Utils;
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
                    + "\n&3/" + label + " reload &8- &7Reload the plugin."
                    + "\n&3/" + label + " debug (all) &8- &7Enable player debug channel."));
            return true;
        }

        Player player;

        /*switch (args[0].toLowerCase()) {
            case "reload":
                if (!sender.hasPermission("hungerisstamina.reload")) {
                    sender.sendMessage(Message.NO_PERM.get());
                    return true;
                }

                plugin.reload(sender);
                break;
        }
        return true;*/

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("hungerisstamina.reload")) {
                sender.sendMessage(Message.NO_PERM.get());
                return true;
            }
            else {
                HungerIsStamina main;
                plugin.reload(sender);
                return true;
            }
        } else {
            return true;
        }
    }
}
