package io.github.lama06.lamagames.event;

import io.github.lama06.lamagames.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GamePlayerJoinedWorldEvent extends GameEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public GamePlayerJoinedWorldEvent(Game game, Player player) {
        super(game);
        this.player = player;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
