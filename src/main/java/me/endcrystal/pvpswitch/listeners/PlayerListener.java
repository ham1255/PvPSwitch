package me.endcrystal.pvpswitch.listeners;

import me.endcrystal.pvpswitch.PvPSwitchPlugin;
import me.endcrystal.pvpswitch.TimerSystem;

import org.bukkit.event.Listener;


public class PlayerListener implements Listener {

    private final TimerSystem timerSystem;

    private final PvPSwitchPlugin plugin;


    public PlayerListener(PvPSwitchPlugin plugin) {
        this.plugin = plugin;
        this.timerSystem = plugin.getTimerSystem();
    }

//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        Player player = event.getPlayer();
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if (!player.isOnline()) {
//                    cancel();
//                    return;
//                }
//                player.sendActionBar(Component.text("(Debug stuff) Pvp Mode: " + timerSystem.isPvpOnFor(player) + " | seconds left: " + timerSystem.leftSeconds(player)));
//            }
//        }.runTaskTimer(plugin, 0, 20);
//    }

}
