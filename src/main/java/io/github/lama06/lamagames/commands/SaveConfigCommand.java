package io.github.lama06.lamagames.commands;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.command.LamaCommand;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class SaveConfigCommand extends LamaCommand {
    public SaveConfigCommand(LamaGamesPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        plugin.saveConfig();

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.stripColor(plugin.getTranslator().translate(Message.COMMAND_LAMAGAMES_SAVE_CONFIG_SUCCESS)));
        } else {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.COMMAND_LAMAGAMES_SAVE_CONFIG_SUCCESS));
        }
    }
}
