package io.github.lama06.lamagames.zombies;

import io.github.lama06.lamagames.util.BlockPosition;
import io.github.lama06.lamagames.zombies.config.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("LamaGames-Zombies")
public class ZombiesConfig implements ConfigurationSerializable {
    public BlockPosition spawn = null;
    public String startingArea = null;
    public PowerSwitchConfig powerSwitch = null;
    public List<ZombieSpawnerConfig> spawners = new ArrayList<>();
    public List<WindowConfig> windows = new ArrayList<>();
    public List<ZombieSpawnLocationConfig> zombieSpawnLocations = new ArrayList<>();
    public List<DoorConfig> doors = new ArrayList<>();
    public List<PerkShopConfig> perkShops = new ArrayList<>();
    public List<ArmorShopConfig> armorShops = new ArrayList<>();

    public ZombiesConfig() {
    }

    @SuppressWarnings({"unused", "unchecked"})
    public ZombiesConfig(Map<String, Object> data) {
        if (data.get("spawn") != null)
            spawn = new BlockPosition((Map<String, Object>) data.get("spawn"));

        if (data.get("starting_area") != null)
            startingArea = (String) data.get("starting_area");

        if (data.get("power_switch") != null)
            powerSwitch = new PowerSwitchConfig((Map<String, Object>) data.get("power_switch"));

        List<Map<String, Object>> serializedSpawners = (List<Map<String, Object>>) data.get("zombie_spawners");
        for (Map<String, Object> serializedSpawner : serializedSpawners) {
            spawners.add(new ZombieSpawnerConfig(serializedSpawner));
        }

        List<Map<String, Object>> serializedWindows = (List<Map<String, Object>>) data.get("windows");
        for (Map<String, Object> serializedWindow : serializedWindows) {
            windows.add(new WindowConfig(serializedWindow));
        }

        List<Map<String, Object>> serializedZombieSpawnLocations = (List<Map<String, Object>>) data.get("additional_zombie_spawn_locations");
        for (Map<String, Object> serializedZombieSpawnLocation : serializedZombieSpawnLocations) {
            zombieSpawnLocations.add(new ZombieSpawnLocationConfig(serializedZombieSpawnLocation));
        }

        List<Map<String, Object>> serializedDoors = (List<Map<String, Object>>) data.get("doors");
        for (Map<String, Object> serializedDoor : serializedDoors) {
            doors.add(new DoorConfig(serializedDoor));
        }

        List<Map<String, Object>> serializedPerkShops = (List<Map<String, Object>>) data.get("perk_shops");
        for (Map<String, Object> serializedPerkShop : serializedPerkShops) {
            perkShops.add(new PerkShopConfig(serializedPerkShop));
        }

        List<Map<String, Object>> serializedArmorShops = (List<Map<String, Object>>) data.get("armor_shops");
        for (Map<String, Object> serializedArmorShop : serializedArmorShops) {
            armorShops.add(new ArmorShopConfig(serializedArmorShop));
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("spawn", spawn == null ? null : spawn.serialize());
        data.put("starting_area", startingArea);
        data.put("power_switch", powerSwitch == null ? null : powerSwitch.serialize());

        List<Map<String, Object>> serializedSpawners = new ArrayList<>();
        for (ZombieSpawnerConfig spawner : spawners) {
            serializedSpawners.add(spawner.serialize());
        }
        data.put("zombie_spawners", serializedSpawners);

        List<Map<String, Object>> serializedWindows = new ArrayList<>();
        for (WindowConfig window : windows) {
            serializedWindows.add(window.serialize());
        }
        data.put("windows", serializedWindows);

        List<Map<String, Object>> serializedZombieSpawnLocations = new ArrayList<>();
        for (ZombieSpawnLocationConfig spawnLocation : zombieSpawnLocations) {
            serializedZombieSpawnLocations.add(spawnLocation.serialize());
        }
        data.put("additional_zombie_spawn_locations", serializedZombieSpawnLocations);

        List<Map<String, Object>> serializedDoors = new ArrayList<>();
        for (DoorConfig door : doors) {
            serializedDoors.add(door.serialize());
        }
        data.put("doors", serializedDoors);

        List<Map<String, Object>> serializedPerkShops = new ArrayList<>();
        for (PerkShopConfig perkShop : perkShops) {
            serializedPerkShops.add(perkShop.serialize());
        }
        data.put("perk_shops", serializedPerkShops);

        List<Map<String, Object>> serializedArmorShops = new ArrayList<>();
        for (ArmorShopConfig armorShop : armorShops) {
            serializedArmorShops.add(armorShop.serialize());
        }
        data.put("armor_shops", serializedArmorShops);

        data.put("data_version", 1);

        return data;
    }
}
