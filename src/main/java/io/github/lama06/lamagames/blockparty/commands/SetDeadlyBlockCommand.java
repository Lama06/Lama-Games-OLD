package io.github.lama06.lamagames.blockparty.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.blockparty.BlockPartyGame;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.game.Game;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDeadlyBlockCommand extends LamaCommand {
    public SetDeadlyBlockCommand(LamaGamesPlugin plugin) {
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

        Material material = Registry.MATERIAL.get(NamespacedKey.minecraft(args[0]));
        if (material == null || !material.isBlock() || material.isAir()) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_SET_DEADLY_BLOCK_MATERIAL_NOT_FOUND));
            return;
        }

        blockParty.getConfig().deadlyBlock = material;
        sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_BLOCK_PARTY, Message.COMMAND_BLOCK_PARTY_SET_DEADLY_BLOCK_SUCCESS));
    }
}
