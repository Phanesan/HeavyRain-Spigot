package tmlust.heavyrain.files;

import org.bukkit.configuration.file.FileConfiguration;
import tmlust.heavyrain.HeavyRain;

public class Config {

    private static final FileConfiguration fileConfig = HeavyRain.getPlugin().getConfig();

    public static void setup(){

        fileConfig.options().copyDefaults(true);
        HeavyRain.getPlugin().saveConfig();

    }

    public static void reload(){
        HeavyRain.getPlugin().saveConfig();
    }
}
