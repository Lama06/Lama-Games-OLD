package io.github.lama06.lamagames.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.GameType;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddGameCommand extends LamaCommand {
    public AddGameCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.ERROR_COMMAND_CONSOLE_NOT_ALLOWED));
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.ERROR_COMMAND_WRONG_USAGE));
            return;
        }

        if (plugin.getGameManager().getGameByWorld(player.getWorld()) != null) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_ADD_GAME_WORLD_ALREADY_HAS_A_GAME));
            return;
        }

        GameType type = GameType.getByName(args[0]);
        if (type == null) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_ADD_GAME_GAME_TYPE_NOT_FOUND));
            return;
        }

        plugin.getGameManager().createGame(player.getWorld(), type);
        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_ADD_GAME_SUCCESS));
    }
}
