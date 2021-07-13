package io.github.lama06.lamagames.translator;

import com.google.common.base.Charsets;
import io.github.lama06.lamagames.LamaGamesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStreamReader;
import java.io.Reader;

public class Translator {
    private final LamaGamesPlugin plugin;
    private Configuration cachedLanguageFile = null;

    public Translator(LamaGamesPlugin plugin) {
        this.plugin = plugin;
    }

    public String translate(String message) {
        Configuration languageFile = getLanguageFile(getSelectedLanguage());
        ConfigurationSection overwrittenMessages = plugin.getConfig().getConfigurationSection("messages");

        String translation;
        if (overwrittenMessages.contains(message)) {
            translation = overwrittenMessages.getString(message);
        } else if (languageFile.contains(message)) {
            translation = languageFile.getString(message);
        } else {
            translation = ChatColor.RED + message;
        }

        return ChatColor.translateAlternateColorCodes('&', translation);
    }

    public String translate(Message message) {
        return translate(message.getName());
    }

    public String translate(Message... messages) {
        StringBuilder builder = new StringBuilder();

        for (Message message : messages) {
            builder.append(translate(message));
        }

        return builder.toString();
    }

    private String getSelectedLanguage() {
        return plugin.getConfig().getString("language");
    }

    private Configuration getLanguageFile(String language) {
        if (cachedLanguageFile != null) {
            return cachedLanguageFile;
        }

        String fileName;

        if (language.equalsIgnoreCase("de")) {
            fileName = "lang-de.yml";
        } else {
            fileName = "lang-en.yml";
        }

        Reader file = new InputStreamReader(plugin.getResource(fileName), Charsets.UTF_8);
        Configuration languageFile = YamlConfiguration.loadConfiguration(file);

        cachedLanguageFile = languageFile;
        return languageFile;
    }
}
