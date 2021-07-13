package io.github.lama06.lamagames.zombies.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class WindowConfig implements ConfigurationSerializable {
    public String name;
    public BlockPosition windowPosition1;
    public BlockPosition windowPosition2;
    public BlockPosition repairPosition1;
    public BlockPosition repairPosition2;

    @SuppressWarnings({"unused", "unchecked"})
    public WindowConfig(Map<String, Object> data) {
        name = (String) data.get("name");
        windowPosition1 = new BlockPosition((Map<String, Object>) data.get("windows_position_1"));
        windowPosition2 = new BlockPosition((Map<String, Object>) data.get("windows_position_2"));
        repairPosition1 = new BlockPosition((Map<String, Object>) data.get("repair_position_1"));
        repairPosition2 = new BlockPosition((Map<String, Object>) data.get("repair_position_2"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("window_position_1", windowPosition1.serialize());
        data.put("windows_position_2", windowPosition2.serialize());
        data.put("repair_position_1", repairPosition1.serialize());
        data.put("repair_position_2", repairPosition2.serialize());
        return data;
    }
}
