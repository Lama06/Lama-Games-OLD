package io.github.lama06.lamagames.game;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.BlockPartyGame;
import io.github.lama06.lamagames.blockparty.config.BlockPartyConfig;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public enum GameType {
    BLOCK_PARTY(BlockPartyGame::new, BlockPartyConfig.class, BlockPartyConfig::new);

    private static final Map<String, GameType> byName = new HashMap<>();

    static {
        for (GameType type : values()) {
            byName.put(type.getName(), type);
        }
    }

    private final GameCreator gameCreator;
    private final Class<? extends ConfigurationSerializable> configType;
    private final Supplier<ConfigurationSerializable> configCreator;

    GameType(GameCreator gameCreator, Class<? extends ConfigurationSerializable> configType, Supplier<ConfigurationSerializable> configCreator) {
        this.gameCreator = gameCreator;
        this.configType = configType;
        this.configCreator = configCreator;
    }

    public static GameType getByName(String name) {
        return byName.get(name.toLowerCase(Locale.ROOT));
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public GameCreator getGameCreator() {
        return gameCreator;
    }

    public Class<? extends ConfigurationSerializable> getConfigType() {
        return configType;
    }

    public Supplier<ConfigurationSerializable> getConfigCreator() {
        return configCreator;
    }

    @FunctionalInterface
    public interface GameCreator {
        Game create(LamaGamesPlugin plugin, World world, ConfigurationSerializable config);
    }
}
