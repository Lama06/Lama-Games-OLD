package io.github.lama06.lamagames.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Util {
    public static Set<BlockPosition> getBlocksInArea(BlockPosition pos1, BlockPosition pos2) {
        BlockPosition lowest = getLowestPosition(pos1, pos2);
        BlockPosition highest = getHighestPosition(pos1, pos2);

        Set<BlockPosition> blocks = new HashSet<>();

        for (int x = lowest.x; x <= highest.x; x++) {
            for (int y = lowest.y; y <= highest.y; y++) {
                for (int z = lowest.z; z <= highest.z; z++) {
                    blocks.add(new BlockPosition(x, y, z));
                }
            }
        }

        return blocks;
    }

    public static BlockPosition getLowestPosition(BlockPosition pos1, BlockPosition pos2) {
        int lowestX = Math.min(pos1.x, pos2.x);
        int lowestY = Math.min(pos1.y, pos2.y);
        int lowestZ = Math.min(pos1.z, pos2.z);
        return new BlockPosition(lowestX, lowestY, lowestZ);
    }

    public static BlockPosition getHighestPosition(BlockPosition pos1, BlockPosition pos2) {
        int highestX = Math.max(pos1.x, pos2.x);
        int highestY = Math.max(pos1.y, pos2.y);
        int highestZ = Math.max(pos1.z, pos2.z);
        return new BlockPosition(highestX, highestY, highestZ);
    }

    public static void fillHotbar(Player player, ItemStack item) {
        for (int i = 0; i <= 8; i++) {
            player.getInventory().setItem(i, item);
        }
    }
}
