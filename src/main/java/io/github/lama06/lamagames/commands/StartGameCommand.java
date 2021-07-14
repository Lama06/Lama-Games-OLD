package io.github.lama06.lamagames.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartGameCommand extends LamaCommand {
    public StartGameCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.ERROR_COMMAND_CONSOLE_NOT_ALLOWED));
            return;
        }

        Game game = plugin.getGameManager().getGameByWorld(player.getWorld());
        if (game == null) {
            return;
        }

        if (game.startGame()) {
            player.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_START_SUCCESS));
        } else {
            player.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_START_FAIL));
        }
    }
}
