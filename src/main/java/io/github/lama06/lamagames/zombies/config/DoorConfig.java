package io.github.lama06.lamagames.zombies.config;

import io.github.lama06.lamagames.util.SerializableBlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoorConfig implements ConfigurationSerializable {
    public String area1;
    public String area2;
    public int gold;
    public List<SerializableBlockData> blocks = new ArrayList<>();

    @SuppressWarnings({"unused", "unchecked"})
    public DoorConfig(Map<String, Object> data) {
        area1 = (String) data.get("area1");
        area2 = (String) data.get("area2");
        gold = (int) data.get("gold");

        List<Map<String, Object>> serializedBlocks = (List<Map<String, Object>>) data.get("blocks");
        for (Map<String, Object> serializedBlock : serializedBlocks) {
            blocks.add(new SerializableBlockData(serializedBlock));
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("area1", area1);
        data.put("area2", area2);
        data.put("gold", gold);

        List<Map<String, Object>> serializedBlocks = new ArrayList<>();
        for (SerializableBlockData block : blocks) {
            serializedBlocks.add(block.serialize());
        }
        data.put("blocks", serializedBlocks);

        return data;
    }
}
