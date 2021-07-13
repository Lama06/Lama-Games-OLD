package io.github.lama06.lamagames.zombies.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class ZombieSpawnLocationConfig implements ConfigurationSerializable {
    public BlockPosition position;
    public String area;

    @SuppressWarnings({"unused", "unchecked"})
    public ZombieSpawnLocationConfig(Map<String, Object> data) {
        position = new BlockPosition((Map<String, Object>) data.get("position"));
        area = (String) data.get("area");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("position", position.serialize());
        data.put("area", area);
        return data;
    }
}
