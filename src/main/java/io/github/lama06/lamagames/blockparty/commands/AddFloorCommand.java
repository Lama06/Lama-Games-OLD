package io.github.lama06.lamagames.blockparty.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.BlockPartyGame;
import io.github.lama06.lamagames.blockparty.config.Floor;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddFloorCommand extends LamaCommand {
    public AddFloorCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_COMMAND_CONSOLE_NOT_ALLOWED));
            return;
        }

        Game game = plugin.getGameManager().getGameByWorld(player.getWorld());
        if (!(game instanceof BlockPartyGame blockParty)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_GAME_NOT_FOUND));
            return;
        }

        if (args.length != 7) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_WRONG_COMMAND_USAGE));
            return;
        }

        String name = args[0];

        if (blockParty.getConfig().getFloorByName(name) != null) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_ADD_FLOOR_NAME_TAKEN));
            return;
        }

        BlockPosition pos1;
        BlockPosition pos2;
        try {
            pos1 = BlockPosition.parse(args[1], args[2], args[3]);
            pos2 = BlockPosition.parse(args[4], args[5], args[6]);
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_WRONG_COMMAND_USAGE));
            return;
        }

        blockParty.getConfig().floors.add(new Floor(name, pos1, pos2));
        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_ADD_FLOOR_SUCCESS));
    }
}
