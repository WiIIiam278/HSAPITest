package me.william278.hsapitest;

import me.william278.husksync.PlayerData;
import me.william278.husksync.bukkit.api.HuskSyncAPI;
import me.william278.husksync.bukkit.data.DataSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class TestCmd implements CommandExecutor {

    private static final HSAPITest plugin = HSAPITest.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            try {
                CompletableFuture<PlayerData> data = HuskSyncAPI.getInstance().getPlayerData(player.getUniqueId());
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    data.thenAccept(playerData -> {
                        ItemStack[] items = new ItemStack[0];
                        try {
                            items = DataSerializer.deserializeInventory(playerData.getSerializedInventory());
                            StringJoiner joiner = new StringJoiner(",");
                            for (ItemStack item : items) {
                                if (item == null) continue;
                                joiner.add(item.getType().name().toLowerCase(Locale.ROOT));
                            }
                            player.sendMessage("Items in inventory: ");
                            player.sendMessage(joiner.toString());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                });
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "An error occurred", e);
            }
            return true;
        }
        return false;
    }
}
