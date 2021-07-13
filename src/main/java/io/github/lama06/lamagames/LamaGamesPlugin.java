package io.github.lama06.lamagames;

import io.github.lama06.lamagames.blockparty.commands.*;
import io.github.lama06.lamagames.blockparty.config.BlockPartyConfig;
import io.github.lama06.lamagames.commands.*;
import io.github.lama06.lamagames.game.GameManager;
import io.github.lama06.lamagames.translator.Translator;
import io.github.lama06.lamagames.zombies.ZombiesConfig;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class LamaGamesPlugin extends JavaPlugin {
    public static final String adminPermission = "lamagames.admin";
    public static final String userPermission = "lamagames.user";

    private Translator translator;
    private GameManager gameManager;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(BlockPartyConfig.class);
        ConfigurationSerialization.registerClass(ZombiesConfig.class);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        translator = new Translator(this);
        gameManager = new GameManager(this);

        getCommand("lamagames-list").setExecutor(new ListGamesCommand(this));
        getCommand("lamagames-add").setExecutor(new AddGameCommand(this));
        getCommand("lamagames-delete").setExecutor(new DeleteGameCommand(this));
        getCommand("lamagames-start").setExecutor(new StartGameCommand(this));
        getCommand("lamagames-stop").setExecutor(new StopGameCommand(this));
        getCommand("lamagames-save").setExecutor(new SaveConfigCommand(this));

        getCommand("blockparty-setSpawn").setExecutor(new SetSpawnPointCommand(this));
        getCommand("blockparty-addFloor").setExecutor(new AddFloorCommand(this));
        getCommand("blockparty-deleteFloor").setExecutor(new DeleteFloorCommand(this));
        getCommand("blockparty-listFloors").setExecutor(new ListFloorsCommand(this));
        getCommand("blockparty-setFloor").setExecutor(new SetDanceFloorCommand(this));
        getCommand("blockparty-setDeadlyBlock").setExecutor(new SetDeadlyBlockCommand(this));

        gameManager.loadGames();
    }

    @Override
    public void onDisable() {
        gameManager.unloadGames();

        saveConfig();
    }

    public Translator getTranslator() {
        return translator;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
