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

    NO_PERM("Insufficient-Permission", "&cYou don't have the permissions to do this!"),

    ONLY_PLAYERS("Console-Sender-Error", "&cI'm sorry but the console can not perform this command!"),

    INVALID_COMMAND("Invalid-Command", "&cThis is not a valid command!"),

    RELOAD("Reload", "&aSuccessfully reloaded Settings.yml, Messages.yml!"),

    DEBUG_ON("Debug-On", "&aYou are now listening to debug about actions caused by you."),

    DEBUG_OFF("Debug-Off", "&cYou are no longer listening to debug."),

    ENABLE_REGEN_CONFLICT("Enable-Regen-Conflict", "&cSimpleStamina is Enabled, Set EnableRegen to false & set stamina regen in MMOCore config!"),

    AUTO_RELOAD("Auto-Reload", "&cAuto reloading plugin to apply changes!"),

    ENABLE_FOOD_FILL_STAMINA_PARTIAL_CONFLICT("Enable-food-fill-stamina-partial-conflict", "&cFoodFillStaminaPartial is not compatible with 'SimpleStamina', Setting FoodFillStaminaPartial to false"),

    SIMPLE_STAMINA_MODE("Simple-Stamina-Mode", "&bSimple Stamina Mode Configured"),
    SIMPLE_STAMINA_MODE_ACTIVATED("Simple-Stamina-Mode-Reload", "&eSimple Stamina Mode Activated"),

    SIMPLE_STAMINA_MODE_EASY("Simple-Stamina-Mode-Easy", "&bSimple Stamina Mode Easy Configured"),
    SIMPLE_STAMINA_MODE_EASY_ACTIVATED("Simple-Stamina-Mode-Easy-Reload", "&eSimple Stamina Mode Easy Activated"),

    SIMPLE_STAMINA_MODE_NORMAL("Simple-Stamina-Mode-Normal", "&bSimple Stamina Mode Normal Configured"),
    SIMPLE_STAMINA_MODE_NORMAL_ACTIVATED("Simple-Stamina-Mode-Normal-Reload", "&eSimple Stamina Mode Normal Activated"),

    SIMPLE_STAMINA_MODE_HARD("Simple-Stamina-Mode-Hard", "&bSimple Stamina Mode Hard Configured"),
    SIMPLE_STAMINA_MODE_HARD_ACTIVATED("Simple-Stamina-Mode-Hard-Reload", "&eSimple Stamina Mode Hard Activated"),

    ADVANCED_STAMINA_MODE("Advanced-Stamina-Mode", "&bAdvanced Stamina Mode Configured"),
    ADVANCED_STAMINA_MODE_ACTIVATED("Advanced-Stamina-Mode-Reload", "&eSimple Stamina Mode Activated"),

    ADVANCED_STAMINA_MODE_EASY("Advanced-Stamina-Mode-Easy", "&bAdvanced Stamina Mode Easy Configured"),
    ADVANCED_STAMINA_MODE_EASY_ACTIVATED("Advanced-Stamina-Mode-Easy-Reload", "&eSimple Stamina Mode Easy Activated"),

    ADVANCED_STAMINA_MODE_NORMAL("Advanced-Stamina-Mode-Normal", "&bAdvanced Stamina Mode Normal Configured"),
    ADVANCED_STAMINA_MODE_NORMAL_ACTIVATED("Advanced-Stamina-Mode-Normal-Reload", "&eAdvanced Stamina Mode Normal Activated"),

    ADVANCED_STAMINA_MODE_HARD("Advanced-Stamina-Mode-Hard", "&bAdvanced Stamina Mode Hard Configured"),
    ADVANCED_STAMINA_MODE_HARD_ACTIVATED("Advanced-Stamina-Mode-Hard-Reload", "&eAdvanced Stamina Mode Hard Activated");


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
