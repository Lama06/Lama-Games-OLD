package io.github.lama06.lamagames.event;

import io.github.lama06.lamagames.game.Game;
import org.bukkit.event.HandlerList;

public class GameCanStartEvent extends GameEvent {
    private static final HandlerList handlers = new HandlerList();
    private boolean canStart = false;

    public GameCanStartEvent(Game game) {
        super(game);
    }

    public boolean getCanStart() {
        return canStart;
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
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
