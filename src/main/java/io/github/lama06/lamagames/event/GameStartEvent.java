package io.github.lama06.lamagames.event;

import io.github.lama06.lamagames.game.Game;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends GameEvent {
    private static final HandlerList handlers = new HandlerList();

    public GameStartEvent(Game game) {
        super(game);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
