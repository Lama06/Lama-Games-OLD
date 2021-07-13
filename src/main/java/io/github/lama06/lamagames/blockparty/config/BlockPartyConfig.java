package io.github.lama06.lamagames.blockparty.config;

import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("LamaGames-BlockPartyConfig")
public class BlockPartyConfig implements ConfigurationSerializable {
    public List<Floor> floors;
    public BlockPosition spawnPoint;
    public BlockPosition danceFloorPosition1;
    public BlockPosition danceFloorPosition2;
    public Material deadlyBlock;
    public RoundTimes roundTimes;

    public BlockPartyConfig() {
        floors = new ArrayList<>();
        spawnPoint = null;
        danceFloorPosition1 = null;
        danceFloorPosition2 = null;
        deadlyBlock = null;
        roundTimes = new RoundTimes();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public BlockPartyConfig(Map<String, Object> data) {
        floors = new ArrayList<>();
        List<Map<String, Object>> serializedFloors = (List<Map<String, Object>>) data.get("floors");
        for (Map<String, Object> serializedFloor : serializedFloors) {
            floors.add(new Floor(serializedFloor));
        }

        danceFloorPosition1 = data.get("dance_floor_position_1") == null ? null : new BlockPosition((Map<String, Object>) data.get("dance_floor_position_1"));
        danceFloorPosition2 = data.get("dance_floor_position_2") == null ? null : new BlockPosition((Map<String, Object>) data.get("dance_floor_position_2"));

        deadlyBlock = data.get("deadly_block") == null ? null : Material.getMaterial((String) data.get("deadly_block"));

        spawnPoint = data.get("spawn_point") == null ? null : new BlockPosition((Map<String, Object>) data.get("spawn_point"));

        roundTimes = new RoundTimes((Map<String, Object>) data.get("round_times"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        List<Map<String, Object>> serializedFloors = new ArrayList<>();
        for (Floor floor : floors) {
            serializedFloors.add(floor.serialize());
        }
        data.put("floors", serializedFloors);

        data.put("dance_floor_position_1", danceFloorPosition1 == null ? null : danceFloorPosition1.serialize());
        data.put("dance_floor_position_2", danceFloorPosition2 == null ? null : danceFloorPosition2.serialize());

        data.put("deadly_block", deadlyBlock == null ? null : deadlyBlock.name());

        data.put("spawn_point", spawnPoint == null ? null : spawnPoint.serialize());

        data.put("round_times", roundTimes.serialize());

        data.put("data_version", 1);

        return data;
    }

    public Floor getFloorByName(String name) {
        for (Floor floor : floors) {
            if (floor.name.equals(name)) {
                return floor;
            }
        }

        return null;
    }
}
