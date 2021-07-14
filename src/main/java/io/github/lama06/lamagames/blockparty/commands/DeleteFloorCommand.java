package io.github.lama06.lamagames.blockparty.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.BlockPartyGame;
import io.github.lama06.lamagames.blockparty.config.Floor;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class DeleteFloorCommand extends LamaCommand {
    public DeleteFloorCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.ERROR_COMMAND_CONSOLE_NOT_ALLOWED));
            return;
        }

        Game game = plugin.getGameManager().getGameByWorld(player.getWorld());
        if (!(game instanceof BlockPartyGame blockParty)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_COMMAND_NOT_IN_GAME_WORLD));
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_COMMAND_WRONG_USAGE));
            return;
        }
        String name = args[0];

        Message message = Message.COMMAND_BLOCK_PARTY_DELETE_FLOOR_NOT_FOUND;

        Iterator<Floor> iterator = blockParty.getConfig().floors.iterator();
        while (iterator.hasNext()) {
            Floor floor = iterator.next();

            if (floor.name.equals(name)) {
                iterator.remove();
                message = Message.COMMAND_BLOCK_PARTY_DELETE_FLOOR_SUCCESS;
                break;
            }
        }

        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, message));
    }
}
