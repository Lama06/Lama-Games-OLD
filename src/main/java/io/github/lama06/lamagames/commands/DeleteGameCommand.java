package io.github.lama06.lamagames.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteGameCommand extends LamaCommand {
    public DeleteGameCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.ERROR_COMMAND_CONSOLE_NOT_ALLOWED));
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.ERROR_COMMAND_WRONG_USAGE));
            return;
        }

        Game game = plugin.getGameManager().getGameByWorld(player.getWorld());
        if (game == null) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.ERROR_COMMAND_NOT_IN_GAME_WORLD));
            return;
        }

        plugin.getGameManager().deleteGame(game);
        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_DELETE_GAME_SUCCESS));
    }
}
