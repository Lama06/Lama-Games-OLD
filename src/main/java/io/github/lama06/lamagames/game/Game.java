package io.github.lama06.lamagames.game;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.event.*;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class Game implements Listener {
    protected final LamaGamesPlugin plugin;
    protected final World world;
    protected final ConfigurationSerializable config;
    protected final GameType type;
    protected boolean running = false;

    public Game(LamaGamesPlugin plugin, World world, ConfigurationSerializable config, GameType type) {
        this.plugin = plugin;
        this.world = world;
        this.config = config;
        this.type = type;
    }

    public void load() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getPluginManager().callEvent(new GameLoadEvent(this));
    }

    public void unload() {
        if (running) {
            endGame();
        }
        Bukkit.getPluginManager().callEvent(new GameUnloadEvent(this));
        HandlerList.unregisterAll(this);
    }

    public boolean startGame() {
        if (running) {
            return false;
        }

        GameCanStartEvent gameCanStartEvent = new GameCanStartEvent(this);
        Bukkit.getPluginManager().callEvent(gameCanStartEvent);
        if (!gameCanStartEvent.getCanStart()) {
            return false;
        }

        running = true;

        broadcast(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.GAME_STARTED_MSG));
        plugin.getLogger().info(plugin.getTranslator().translate(Message.GAME_STARTED_CONSOLE_MSG) + world.getName());

        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));

        return true;
    }

    public boolean endGame() {
        if (!running) {
            return false;
        }

        running = false;

        broadcast(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.GAME_ENDED_MSG));
        plugin.getLogger().info(plugin.getTranslator().translate(Message.GAME_ENDED_CONSOLE_MSG) + world.getName());

        Bukkit.getPluginManager().callEvent(new GameEndEvent(this));

        return true;
    }

    protected void broadcast(String msg) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(msg);
        }
    }

    // EventHandlers

    @EventHandler
    public void game_handlePlayerChangeWorldEvent(PlayerChangedWorldEvent event) {
        if (event.getFrom().equals(world)) {
            Bukkit.getPluginManager().callEvent(new GamePlayerLeftWorldEvent(this, event.getPlayer()));
        } else if (event.getPlayer().getWorld().equals(world)) {
            Bukkit.getPluginManager().callEvent(new GamePlayerJoinedWorldEvent(this, event.getPlayer()));
        }
    }

    @EventHandler
    public void game_handlePlayerJoinEvent(PlayerJoinEvent event) {
        if (event.getPlayer().getWorld().equals(world)) {
            Bukkit.getPluginManager().callEvent(new GamePlayerJoinedWorldEvent(this, event.getPlayer()));
        }
    }

    @EventHandler
    public void game_handlePlayerQuitEvent(PlayerQuitEvent event) {
        if (event.getPlayer().getWorld().equals(world)) {
            Bukkit.getPluginManager().callEvent(new GamePlayerLeftWorldEvent(this, event.getPlayer()));
        }
    }

    public World getWorld() {
        return world;
    }

    public ConfigurationSerializable getConfig() {
        return config;
    }

    public GameType getType() {
        return type;
    }

    public LamaGamesPlugin getPlugin() {
        return plugin;
    }

    public boolean isRunning() {
        return running;
    }
}
