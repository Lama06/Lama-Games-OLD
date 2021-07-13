package io.github.lama06.lamagames.blockparty.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Floor implements ConfigurationSerializable {
    public String name;
    public BlockPosition position1;
    public BlockPosition position2;

    public Floor(String name, BlockPosition position1, BlockPosition position2) {
        this.name = name;
        this.position1 = position1;
        this.position2 = position2;
    }

    @SuppressWarnings({"unused", "unchecked"})
    public Floor(Map<String, Object> data) {
        position1 = new BlockPosition((Map<String, Object>) data.get("position1"));
        position2 = new BlockPosition((Map<String, Object>) data.get("position2"));
        name = (String) data.get("name");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("position1", position1.serialize());
        data.put("position2", position2.serialize());
        return data;
    }
}
