package io.github.lama06.lamagames.util;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class SerializableBlockData implements ConfigurationSerializable {
    public BlockPosition position;
    public BlockData state;

    @SuppressWarnings({"unused", "unchecked"})
    public SerializableBlockData(Map<String, Object> data) {
        position = new BlockPosition((Map<String, Object>) data.get("position"));
        state = Bukkit.createBlockData((String) data.get("data"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("position", position.serialize());
        data.put("data", state.getAsString());
        return data;
    }
}
