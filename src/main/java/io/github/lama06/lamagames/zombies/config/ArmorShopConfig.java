package io.github.lama06.lamagames.zombies.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class ArmorShopConfig implements ConfigurationSerializable {
    public BlockPosition position;
    public int gold;

    @SuppressWarnings({"unused", "unchecked"})
    public ArmorShopConfig(Map<String, Object> data) {
        position = new BlockPosition((Map<String, Object>) data.get("position"));
        gold = (int) data.get("gold");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("position", position);
        data.put("gold", gold);
        return data;
    }
}
