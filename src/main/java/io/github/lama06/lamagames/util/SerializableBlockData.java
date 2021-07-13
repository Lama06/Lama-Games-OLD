package io.github.lama06.lamagames.util;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class SerializableBlockData implements ConfigurationSerializable {
    public int x;
    public int y;
    public int z;
    public BlockData state;

    @SuppressWarnings("unused")
    public SerializableBlockData(Map<String, Object> data) {
        x = (int) data.get("x");
        y = (int) data.get("y");
        z = (int) data.get("z");
        state = Bukkit.createBlockData((String) data.get("data"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("x", x);
        data.put("y", y);
        data.put("z", z);
        data.put("data", state.getAsString());
        return data;
    }
}
