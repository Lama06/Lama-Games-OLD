package io.github.lama06.lamagames.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class BlockPosition implements ConfigurationSerializable {
    public int x;
    public int y;
    public int z;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPosition(Location location) {
        this(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @SuppressWarnings("unused")
    public BlockPosition(Map<String, Object> data) {
        x = (int) data.get("x");
        y = (int) data.get("y");
        z = (int) data.get("z");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("x", x);
        data.put("y", y);
        data.put("z", z);
        return data;
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    public static BlockPosition parse(String arg1, String arg2, String arg3) {
        return new BlockPosition(Integer.parseInt(arg1), Integer.parseInt(arg2), Integer.parseInt(arg3));
    }
}
