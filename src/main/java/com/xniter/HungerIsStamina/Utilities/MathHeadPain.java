package com.xniter.HungerIsStamina.Utilities;

import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;

public class MathHeadPain {

    // Not used, was used but now is not, I saved this here for later so that I can look back on it.
    // I struggle with match so having this here is a good documentation for me.
    private int calc(PlayerData data) {
        return (int)Math.ceil(Math.min(20.0D, Math.max(0.0D, data.getStamina() / data.getStats().getStat(StatType.MAX_STAMINA) * 20.0D)));
    }
}
