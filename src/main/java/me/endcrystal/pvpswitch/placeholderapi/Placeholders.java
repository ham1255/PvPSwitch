package me.endcrystal.pvpswitch.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.endcrystal.pvpswitch.TimerSystem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholders extends PlaceholderExpansion {
    private final TimerSystem timerSystem;

    public Placeholders(TimerSystem timerSystem) {
        this.timerSystem = timerSystem;
        register();
    }

    @Override
    public @NotNull String getAuthor() {
        return "Xymb";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "endcrystal-pvpswitch";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return false;
    }


    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("players-in-pvp")) {
            return String.valueOf(timerSystem.cachedTotalPlayersInPvP());
        }
        if (player != null) {
            if (params.equalsIgnoreCase("seconds-left")) {
                return String.valueOf(timerSystem.leftSeconds(player));
            }
            if (params.equalsIgnoreCase("pvp-mode")) {
                return String.valueOf(timerSystem.isPvpOnFor(player));
            }
            if (params.equalsIgnoreCase("pvp-description")) {
                if (timerSystem.isPvpOnFor(player)) {
                    return "PVP Anti-Cheat: " + String.valueOf(timerSystem.leftSeconds(player)) + "s";
                }
                return "";
            }
        }

        return "null";
    }
}
