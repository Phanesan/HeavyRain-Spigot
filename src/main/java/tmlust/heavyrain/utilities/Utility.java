package tmlust.heavyrain.utilities;

import org.bukkit.World;
import org.bukkit.entity.Player;
import tmlust.heavyrain.HeavyRain;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class Utility {

    public static boolean isOnlyNumbers(char[] s) {
        for (char c : s) {
            if (!(c >= 48 && c <= 57)) {
                return false;
            }
        }
        return true;
    }

    public static List<Player> getConfigWorldPlayers(HeavyRain plugin, String configString) throws RuntimeException {
        World w = plugin.getServer().getWorld(plugin.getConfigFile().getConfigFile().getString(configString));
        if(w != null) {
            return w.getPlayers();
        } else
            throw new RuntimeException("Algo salio mal y no se encontro el mundo " + configString);
    }

    public static List<Player> getConfigWorldsPlayers(HeavyRain plugin,
                                                String worldConfig,
                                                String world_netherConfig,
                                                String world_the_endConfig){

        List<Player> players = new ArrayList<>();

        players.addAll(getConfigWorldPlayers(plugin,worldConfig));
        players.addAll(getConfigWorldPlayers(plugin,world_netherConfig));
        players.addAll(getConfigWorldPlayers(plugin,world_the_endConfig));

        return players;
    }

    public static World findWorldConfig(HeavyRain plugin, String configString) throws RuntimeException {
        World w = plugin.getServer().getWorld(plugin.getConfigFile().getConfigFile().getString(configString));

        if(w != null) {
            return w;
        } else
            throw new RuntimeException("Algo salio mal y no se encontro el mundo " + configString);
    }

    public static boolean randomBoolean(double probability) {
        return (Math.random()) < (probability/100);
    }

}
