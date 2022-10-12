package tmlust.heavyrain.files;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import tmlust.heavyrain.HeavyRain;

import java.io.File;
import java.io.IOException;

public class Config {

    private Plugin plugin = null;
    // Config file
    private FileConfiguration config;
    private File configFile;

    public Config(HeavyRain plugin){
        this.plugin = plugin;
    }

    public void setup(){
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reload(CommandSender sender){
        config = YamlConfiguration.loadConfiguration(configFile);
        sender.sendMessage(ChatColor.GREEN + "El plugin ha sido recargado.");
    }

    public FileConfiguration getConfigFile(){
        return config;
    }
}
