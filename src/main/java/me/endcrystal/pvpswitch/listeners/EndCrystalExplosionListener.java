package me.endcrystal.pvpswitch.listeners;

import me.endcrystal.pvpswitch.PvPSwitchPlugin;
import me.endcrystal.pvpswitch.TimerSystem;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Collection;

public class EndCrystalExplosionListener implements Listener {

    private final TimerSystem timerSystem;
    public EndCrystalExplosionListener(PvPSwitchPlugin plugin) {
        this.timerSystem = plugin.getTimerSystem();
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        if (event.getEntity().getType() == EntityType.ENDER_CRYSTAL) {
            // get explosion location
            final Location explosionLocation = event.getLocation();
            Collection<Player> players = explosionLocation.getNearbyEntitiesByType(Player.class, 50.0); // Todo: should not hard code?
            for (Player player : players) {
                timerSystem.setPvpOnFor(player);
            }
        }
    }


}
