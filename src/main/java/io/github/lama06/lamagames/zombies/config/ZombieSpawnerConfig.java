package io.github.lama06.lamagames.zombies.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class ZombieSpawnerConfig implements ConfigurationSerializable {
    public String name;
    public String area;
    public String window;
    public BlockPosition position;

    @SuppressWarnings({"unused", "unchecked"})
    public ZombieSpawnerConfig(Map<String, Object> data) {
        name = (String) data.get("name");
        area = (String) data.get("area");
        window = (String) data.get("window");
        position = new BlockPosition((Map<String, Object>) data.get("position"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("area", area);
        data.put("window", window);
        data.put("position", position.serialize());
        return data;
    }
}
