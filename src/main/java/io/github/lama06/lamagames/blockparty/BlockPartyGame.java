package io.github.lama06.lamagames.blockparty;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.config.BlockPartyConfig;
import io.github.lama06.lamagames.blockparty.config.Floor;
import io.github.lama06.lamagames.event.*;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.game.GameType;
import io.github.lama06.lamagames.util.BlockPosition;
import io.github.lama06.lamagames.util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BlockPartyGame extends Game {
    private final Random random = new Random();
    private BukkitTask currentTask = null;

    public BlockPartyGame(LamaGamesPlugin plugin, World world, ConfigurationSerializable config) {
        super(plugin, world, config, GameType.BLOCK_PARTY);

        if (!(config instanceof BlockPartyConfig)) {
            throw new IllegalArgumentException();
        }
    }

    @EventHandler
    public void handleGameCanStartEvent(GameCanStartEvent event) {
        boolean canStart = world.getPlayers().size() != 0;

        if (getConfig().floors.size() == 0) {
            canStart = false;
        }

        if (getConfig().spawnPoint == null || getConfig().deadlyBlock == null || getConfig().danceFloorPosition2 == null || getConfig().danceFloorPosition2 == null) {
            canStart = false;
        }

        event.setCanStart(canStart);
    }

    @EventHandler
    public void handleGameStartEvent(GameStartEvent event) {
        if (!event.getGame().equals(this)) {
            return;
        }

        for (Player player : world.getPlayers()) {
            player.teleport(getConfig().spawnPoint.toLocation(world));
            player.setGameMode(GameMode.ADVENTURE);
        }

        startRound(1);
    }

    @EventHandler
    public void handleGameEnded(GameEndEvent event) {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }

        if (getConfig().floors.size() >= 1) {
            applyFloor(getConfig().floors.get(0));
        }

        for (Player player : world.getPlayers()) {
            Util.fillHotbar(player, null);
        }
    }

    @EventHandler
    public void handlePlayerMoveEvent(PlayerMoveEvent event) {
        if (!running || !event.getPlayer().getWorld().equals(world) || !isAlive(event.getPlayer())) {
            return;
        }

        Block standingOn = world.getBlockAt(event.getTo().clone().subtract(0, 1, 0));
        if (standingOn.getType() == getConfig().deadlyBlock) {
            kill(event.getPlayer());
        }
    }

    @EventHandler
    public void handlePlayerJoined(GamePlayerJoinedWorldEvent event) {
        if (!event.getGame().equals(this)) {
            return;
        }

        if (running) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        } else {
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
        }

        event.getPlayer().teleport(getConfig().spawnPoint.toLocation(world));
    }

    @EventHandler
    public void handlePlayerLeft(GamePlayerLeftWorldEvent event) {
        if (!event.getGame().equals(this)) {
            return;
        }

        if (isEveryoneDead(event.getPlayer())) {
            endGame();
        }
    }

    @EventHandler
    public void handleFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().equals(world)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getWorld().equals(world)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        if (event.getPlayer().getWorld().equals(world) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().equals(world) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    private void startRound(int round) {
        Floor floor = getRandomFloor();
        applyFloor(floor);

        currentTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            int time = getConfig().roundTimes.getTime(round);
            DyeColor color = getRandomColor(floor);
            Material wool = Material.getMaterial(color.name() + "_WOOL");

            for (Player player : world.getPlayers()) {
                if (!isAlive(player)) {
                    continue;
                }

                player.getInventory().clear();
                Util.fillHotbar(player, new ItemStack(wool));
                player.sendTitle(color.name().replace('_', ' '), Integer.toString(round), 0, time, 10);
            }

            currentTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                removeFloor(color);

                for (Player player : world.getPlayers()) {
                    if (!isAlive(player)) {
                        continue;
                    }

                    Util.fillHotbar(player, null);
                }

                currentTask = Bukkit.getScheduler().runTaskLater(plugin, () -> startRound(round + 1), 60);
            }, time);
        }, 60);
    }

    private void applyFloor(Floor floor) {
        BlockPosition sourceStart = Util.getLowestPosition(floor.position1, floor.position2);
        Set<BlockPosition> sourceBlocks = Util.getBlocksInArea(floor.position1, floor.position2);
        BlockPosition targetStart = Util.getLowestPosition(getConfig().danceFloorPosition1, getConfig().danceFloorPosition2);

        for (BlockPosition sourcePosition : sourceBlocks) {
            Block source = world.getBlockAt(sourcePosition.x, sourcePosition.y, sourcePosition.z);

            int relativeX = sourcePosition.x - sourceStart.x;
            int relativeY = sourcePosition.y - sourceStart.y;
            int relativeZ = sourcePosition.z - sourceStart.z;

            Block target = world.getBlockAt(targetStart.x + relativeX, targetStart.y + relativeY, targetStart.z + relativeZ);

            target.setBlockData(source.getBlockData().clone());
        }
    }

    private void removeFloor(DyeColor ignore) {
        for (BlockPosition position : Util.getBlocksInArea(getConfig().danceFloorPosition1, getConfig().danceFloorPosition2)) {
            Block block = world.getBlockAt(position.x, position.y, position.z);
            if (!block.getType().name().startsWith(ignore.name())) {
                block.setType(Material.AIR);
            }
        }
    }

    private List<DyeColor> getColors(Floor floor) {
        List<DyeColor> colors = new ArrayList<>();
        for (BlockPosition position : Util.getBlocksInArea(floor.position1, floor.position2)) {
            Block block = world.getBlockAt(position.x, position.y, position.z);

            for (DyeColor color : DyeColor.values()) {
                if (block.getType().name().startsWith(color.name())) {
                    colors.add(color);
                    break;
                }
            }
        }
        return colors;
    }

    private DyeColor getRandomColor(Floor floor) {
        List<DyeColor> colors = getColors(floor);
        return colors.get(random.nextInt(colors.size()));
    }

    private boolean isAlive(Player player) {
        return player.getGameMode() != GameMode.SPECTATOR;
    }

    private void kill(Player target) {
        target.setGameMode(GameMode.SPECTATOR);

        if (isEveryoneDead()) {
            endGame();
        }
    }

    private boolean isEveryoneDead(Player ignore) {
        boolean result = true;
        for (Player player : world.getPlayers()) {
            if (isAlive(player) && !player.equals(ignore)) {
                result = false;
            }
        }
        return result;
    }

    private boolean isEveryoneDead() {
        return isEveryoneDead(null);
    }

    private Floor getRandomFloor() {
        return getConfig().floors.get(random.nextInt(getConfig().floors.size()));
    }

    @Override
    public BlockPartyConfig getConfig() {
        return (BlockPartyConfig) config;
    }
}
