package tmlust.heavyrain.files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tmlust.heavyrain.HeavyRain;

import java.io.File;
import java.io.IOException;

public class Config {

    private static FileConfiguration fileConfig = HeavyRain.getInstance().getConfig();

    public static void setup(){

        fileConfig.options().copyDefaults(true);
        HeavyRain.getInstance().saveConfig();

    }

    public static void reload(){
        HeavyRain.getInstance().saveConfig();
    }
}
