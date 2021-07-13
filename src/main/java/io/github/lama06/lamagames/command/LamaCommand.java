package io.github.lama06.lamagames.command;

import io.github.lama06.lamagames.LamaGamesPlugin;
import io.github.lama06.lamagames.translator.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class LamaCommand implements CommandExecutor {
    protected final LamaGamesPlugin plugin;
    protected String permission = LamaGamesPlugin.adminPermission;

    public LamaCommand(LamaGamesPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(plugin.getTranslator().translate(Message.PREFIX_MAIN, Message.ERROR_COMMAND_PERMISSION_REQUIRED));
            return true;
        }

        execute(sender, label, args);

        return true;
    }
}
