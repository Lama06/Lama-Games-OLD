package io.github.lama06.lamagames.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.CommandSender;

public class ListGamesCommand extends LamaCommand {
    public ListGamesCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (plugin.getGameManager().getGames().size() == 0) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_LIST_NO_GAMES));
            return;
        }

        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_LIST_HEADER));

        for (Game game : plugin.getGameManager().getGames()) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN) + String.format("%s -> %s", game.getWorld().getName(), game.getType().toString()));
        }
    }
}
