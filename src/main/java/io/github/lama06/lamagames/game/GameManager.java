package io.github.lama06.lamagames.game;

import io.github.lama06.lamagames.LamaGamesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameManager {
    private final LamaGamesPlugin plugin;
    private final Set<Game> games = new HashSet<>();

    public GameManager(LamaGamesPlugin plugin) {
        this.plugin = plugin;
    }

    public ConfigurationSection getGamesConfiguration() {
        return plugin.getConfig().getConfigurationSection("games");
    }

    public void loadGames() {
        ConfigurationSection gamesConfiguration = getGamesConfiguration();

        for (String worldName : gamesConfiguration.getKeys(false)) {
            ConfigurationSection worldConfig = gamesConfiguration.getConfigurationSection(worldName);

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                gamesConfiguration.set(worldName, null);
                continue;
            }

            GameType type = GameType.getByName(worldConfig.getString("type"));
            if (type == null) {
                gamesConfiguration.set(worldName, null);
                continue;
            }

            ConfigurationSerializable gameConfig = worldConfig.getSerializable("config", type.getConfigType());

            Game game = type.getGameCreator().create(plugin, world, gameConfig);
            games.add(game);
            game.load();
        }
    }

    public void unloadGames() {
        Iterator<Game> iterator = games.iterator();
        while (iterator.hasNext()) {
            Game game = iterator.next();
            game.unload();
            iterator.remove();
        }
    }

    public void createGame(World world, GameType type) {
        ConfigurationSerializable gameConfig = type.getConfigCreator().get();

        ConfigurationSection worldConfig = getGamesConfiguration().createSection(world.getName());
        worldConfig.set("type", type.getName());
        worldConfig.set("config", gameConfig);

        Game game = type.getGameCreator().create(plugin, world, gameConfig);
        games.add(game);
        game.load();
    }

    public void deleteGame(Game game) {
        game.unload();
        getGamesConfiguration().set(game.getWorld().getName(), null);
        games.remove(game);
    }

    public Game getGameByWorld(World world) {
        for (Game game : games) {
            if (game.getWorld().equals(world)) {
                return game;
            }
        }

        return null;
    }

    public Set<Game> getGames() {
        return games;
    }
}
