package io.github.lama06.lamagames.blockparty.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.BlockPartyGame;
import io.github.lama06.lamagames.blockparty.config.Floor;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListFloorsCommand extends LamaCommand {
    public ListFloorsCommand(LamaGamesPlugin plugin) {
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

        if (blockParty.getConfig().floors.size() == 0) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_BLOCK_PARTY_LIST_FLOORS_NO_FLOORS));
            return;
        }

        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_LIST_FLOORS_HEADER));

        for (Floor floor : blockParty.getConfig().floors) {
            sender.sendMessage(
                    plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY) +
                            String.format("%s -> %s - %s", floor.name, floor.position1, floor.position2)
            );
        }
    }
}
