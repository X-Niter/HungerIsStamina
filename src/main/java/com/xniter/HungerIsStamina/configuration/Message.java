package com.xniter.HungerIsStamina.configuration;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.Utilities.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Message system, loaded on enable & reload.
 *
 * @author X_Niter
 */
public enum Message {

    PREFIX("Prefix", "&e[&bHIS&e] &r"),

    UPDATE("Update", "&6&m-----&r &3&lHungerIsStamina &6&m-----\n" +
            "&eA new update was found!\n" +
            "&eCurrent version: &c%version%\n" +
            "&eNew version: &a%newVersion%\n" +
            "&6&m-----------------------"),

    /**
     * Command general messages.
     */
    NO_PERM("Insufficient-Permission", "&cYou don't have the permissions to do this!"),
    ONLY_PLAYERS("Console-Sender-Error", "&cI'm sorry but the console can not perform this command!"),
    INVALID_COMMAND("Invalid-Command", "&cThis is not a valid command!"),

    RELOAD("Reload", "&aSuccessfully reloaded Settings.yml, Messages.yml!"),

    /**
     * Debug
     */
    DEBUG_ON("Debug-On", "&aYou are now listening to debug about actions caused by you."),
    DEBUG_OFF("Debug-Off", "&cYou are no longer listening to debug.");

    @Getter
    private final String path;

    @Getter
    @Setter
    private String value;

    @Getter
    private static boolean insertPrefix = false;

    public String get() {
        return Utils.color(Utils.parse(insertPrefix ? "%prefix%" + this.value : this.value));
    }

    public String get(Player player) {
        return Utils.color(Utils.parse(insertPrefix ? "%prefix%" + this.value : this.value, player));
    }

    Message(String path, String value) {
        this.path = path;
        this.value = value;
    }

    public static void load() {

        FileConfiguration messages = HungerIsStamina.getInstance().getFiles().getMessages().getFileConfiguration();

        if (!messages.contains("Insert-Prefix"))
            messages.set("Insert-Prefix", true);
        insertPrefix = messages.getBoolean("Insert-Prefix", true);

        for (Message msg : values()) {
            String str = messages.getString("Messages." + msg.getPath());

            if (str == null) {
                messages.set("Messages." + msg.getPath(), msg.getValue());
                continue;
            }

            msg.setValue(str);
        }

        HungerIsStamina.getInstance().getFiles().getMessages().save();
    }
}
