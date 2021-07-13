package io.github.lama06.lamagames.event;

import io.github.lama06.lamagames.game.Game;
import org.bukkit.event.Event;

public abstract class GameEvent extends Event {
    private final Game game;

    public GameEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
