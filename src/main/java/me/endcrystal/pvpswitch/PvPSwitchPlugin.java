package me.endcrystal.pvpswitch;

import me.endcrystal.pvpswitch.listeners.EndCrystalExplosionListener;
import me.endcrystal.pvpswitch.listeners.PlayerListener;
import me.endcrystal.pvpswitch.placeholderapi.Placeholders;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPSwitchPlugin extends JavaPlugin {

    private final TimerSystem timerSystem;

    public PvPSwitchPlugin() {
        this.timerSystem = new TimerSystem();
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EndCrystalExplosionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        timerSystem.startInternalTasks(this);
        new Placeholders(timerSystem);
    }


    @Override
    public void onDisable() {
        timerSystem.cancel();
    }

    public TimerSystem getTimerSystem() {
        return timerSystem;
    }
}
