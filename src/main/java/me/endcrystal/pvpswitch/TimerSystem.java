package me.endcrystal.pvpswitch;

import com.github.puregero.multilib.MultiLib;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.endcrystal.pvpswitch.timer.model.PlayerTimerSwitch;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TimerSystem extends BukkitRunnable {

    private static TimerSystem INSTANCE;
    private final ConcurrentHashMap<Player, PlayerTimerSwitch> time;
    private final AtomicInteger cachedTotalPlayersInPvP = new AtomicInteger(0);

    private BukkitTask evictionTask;

    public TimerSystem() {
        this.time = new ConcurrentHashMap<>();
        INSTANCE = this;
    }

    private PlayerTimerSwitch get(Player player) {
        if (!player.isOnline()) {
            // so we don't need to store this into the map incase something randomly request, and we evicted the entry
            return new PlayerTimerSwitch().expiredByDefault();
        }
        return time.computeIfAbsent(player, (player1) -> new PlayerTimerSwitch().expiredByDefault());
    }

    public boolean isPvpOnFor(Player player) {
        return !get(player).isExpired();
    }

    public long leftSeconds(Player player) {
        PlayerTimerSwitch timerSwitch = get(player);
        return timerSwitch.leftSeconds();
    }

    public void setPvpOnFor(Player player) {
        PlayerTimerSwitch timerSwitch = get(player);
        timerSwitch.updateExpiry();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(player.getUniqueId().toString());
        MultiLib.notify("set_pvp_switch", out.toByteArray());
    }

    public void setPvpOnFor(Player player, long seconds) {
        PlayerTimerSwitch timerSwitch = get(player);
        timerSwitch.updateExpiry(seconds);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(player.getUniqueId().toString());
        out.writeLong(seconds);
        MultiLib.notify("set_pvp_switch_custom", out.toByteArray());
    }

    public int cachedTotalPlayersInPvP() {
        return this.cachedTotalPlayersInPvP.get();
    }


    private int totalPlayersInPvP() {
        AtomicInteger total = new AtomicInteger(0);
        this.time.forEach((player, timerSwitch) -> {
            if (player.isOnline() && !timerSwitch.isExpired()) total.getAndIncrement();
        });
        return total.get();
    }

    private static class EvictionTask extends BukkitRunnable {

        @Override
        public void run() {

        }
    }

    @Override
    public void run() {
        this.cachedTotalPlayersInPvP.set(totalPlayersInPvP());
    }

    void startInternalTasks(Plugin plugin) {
        MultiLib.on(plugin, "set_pvp_switch_custom", (data) -> {
            ByteArrayDataInput input = ByteStreams.newDataInput(data);
            Player player = Bukkit.getPlayer(UUID.fromString(input.readUTF()));
            if (player == null) return;
            Long seconds = input.readLong();
            get(player).updateExpiry(seconds);

        });
        MultiLib.on(plugin, "set_pvp_switch", (data) -> {
            ByteArrayDataInput input = ByteStreams.newDataInput(data);
            Player player = Bukkit.getPlayer(UUID.fromString(input.readUTF()));
            if (player == null) return;
            get(player).updateExpiry();

        });
        this.evictionTask = new EvictionTask().runTaskTimerAsynchronously(plugin, 20 * 120, 20 * 120);

        // clean up
        runTaskTimerAsynchronously(plugin, 20 * 2, 20 * 2);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        this.evictionTask.cancel();
    }

    public static TimerSystem get() {
        return INSTANCE;
    }


}
