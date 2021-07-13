package io.github.lama06.lamagames.blockparty.config;

import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class RoundTimes implements ConfigurationSerializable {
    private static final Map<Integer, Integer> defaultTimes = ImmutableMap.<Integer, Integer>builder()
            .put(1, 120)
            .put(2, 100)
            .put(3, 90)
            .put(4, 80)
            .put(5, 70)
            .put(6, 60)
            .put(7, 50)
            .put(8, 40)
            .put(9, 30)
            .put(10, 25)
            .put(0, 20)
            .build();

    private final Map<Integer, Integer> times;

    public RoundTimes() {
        times = defaultTimes;
    }

    @SuppressWarnings("unused")
    public RoundTimes(Map<String, Object> data) {
        times = new HashMap<>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            times.put(Integer.parseInt(entry.getKey()), (int) entry.getValue());
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : times.entrySet()) {
            data.put(entry.getKey().toString(), entry.getValue());
        }

        return data;
    }

    public int getTime(int round) {
        return times.getOrDefault(round, times.get(0));
    }

    public Map<Integer, Integer> getTimes() {
        return times;
    }
}
