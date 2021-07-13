package io.github.lama06.lamagames.blockparty.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.BlockPartyGame;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import io.github.lama06.lamagames.util.BlockPosition;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDanceFloorCommand extends LamaCommand {
    public SetDanceFloorCommand(LamaGamesPlugin plugin) {
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

        if (args.length != 1) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_WRONG_COMMAND_USAGE));
            return;
        }

        Block targeted = player.getTargetBlockExact(5);
        if (targeted == null) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_NO_TARGETED_BLOCK));
            return;
        }

        BlockPosition position = new BlockPosition(targeted.getX(), targeted.getY(), targeted.getZ());

        if (args[0].equals("1")) {
            blockParty.getConfig().danceFloorPosition1 = position;
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_SET_DANCE_FLOOR_SUCCESS));
        } else if (args[0].equals("2")) {
            blockParty.getConfig().danceFloorPosition2 = position;
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_SET_DANCE_FLOOR_SUCCESS));
        } else {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.ERROR_WRONG_COMMAND_USAGE));
        }
    }
}
