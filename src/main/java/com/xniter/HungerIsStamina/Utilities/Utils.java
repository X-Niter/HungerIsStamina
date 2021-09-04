package com.xniter.HungerIsStamina.Utilities;

import com.xniter.HungerIsStamina.HungerIsStamina;
import com.xniter.HungerIsStamina.configuration.Message;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class Utils {

    public final List<String> bypass = new ArrayList<>();
    public final List<String> dataCheck = new ArrayList<>();
    public final List<Color> colors = new ArrayList<>();

    static {
        colors.add(Color.AQUA);
        colors.add(Color.BLUE);
        colors.add(Color.FUCHSIA);
        colors.add(Color.GREEN);
        colors.add(Color.LIME);
        colors.add(Color.ORANGE);
        colors.add(Color.WHITE);
        colors.add(Color.YELLOW);
    }

    public final Map<String, Boolean> events = new HashMap<>();
    public final Map<String, BossBar> bars = new HashMap<>();

    public String parse(String string) {
        string = string.replaceAll("(?i)%prefix%", Message.PREFIX.getValue());
        return string;
    }

    public String parse(String string, Player player) {
        string = Utils.parse(string);
        string = string.replaceAll("(?i)%player%", player.getName());
        if (HungerIsStamina.getInstance().isUsePlaceholderAPI())
            string = PlaceholderAPI.setPlaceholders(player, string);
        return string;
    }

    public String stripColor(String msg) {
        return msg != null ? ChatColor.stripColor(msg) : null;
    }

    @Nullable
    public String color(@Nullable String msg) {
        return color(msg, '&');
    }

    @Nullable
    public String color(@Nullable String msg, char colorChar) {
        return msg == null ? null : ChatColor.translateAlternateColorCodes(colorChar, msg);
    }

}
