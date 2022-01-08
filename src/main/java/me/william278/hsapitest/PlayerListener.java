package me.william278.hsapitest;

import me.william278.huskhomes2.api.HuskHomesAPI;
import me.william278.huskhomes2.teleport.points.TeleportationPoint;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.UUID;

public class PlayerListener implements Listener {

    private static final HashSet<UUID> trackingPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        final Location location = e.getTo();
        if (!trackingPlayers.contains(e.getPlayer().getUniqueId())) return;
        if (location == null) return;
        if (location.getX() >= 300) {
            HuskHomesAPI.getInstance().teleportPlayer(e.getPlayer(), new TeleportationPoint("world",
                    301d, location.getY(), location.getZ(), location.getYaw(), location.getPitch(), "beta"),
                    false);
            trackingPlayers.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        trackingPlayers.add(e.getPlayer().getUniqueId());
    }

}
