package io.github.lama06.lamagames.zombies.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class PowerSwitchConfig implements ConfigurationSerializable {
    public int gold;
    public BlockPosition position;

    public PowerSwitchConfig(int gold, BlockPosition position) {
        this.gold = gold;
        this.position = position;
    }

    @SuppressWarnings({"unused", "unchecked"})
    public PowerSwitchConfig(Map<String, Object> data) {
        gold = (int) data.get("gold");
        position = new BlockPosition((Map<String, Object>) data.get("position"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("gold", gold);
        data.put("position", position.serialize());
        return data;
    }
}
