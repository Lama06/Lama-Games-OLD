package io.github.lama06.lamagames.event;

import io.github.lama06.lamagames.game.Game;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends GameEvent {
    private static final HandlerList handlers = new HandlerList();

    public GameEndEvent(Game game) {
        super(game);
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
