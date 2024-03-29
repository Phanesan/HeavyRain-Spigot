package tmlust.heavyrain.tasks;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.utils.Utility;

import java.util.List;

public class HeavyRainTask extends BukkitRunnable {
    HeavyRain instance;

    public HeavyRainTask(HeavyRain instance){
        this.instance = instance;
    }

    @Override
    public void run() {

        instance.getData().setHeavyRainActivated(true);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "HEAVYRAIN INICIADA! ");
        List<Player> players = Utility.getConfigWorldsPlayers(instance,"heavyrain_world","heavyrain_world_nether","heavyrain_world_the_end");

        for(Player p : players) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l&kiii &4&l¡EL CIELO SE HA OSCURECIDO! &c&l&kiii"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lMientras la HeavyRain este activa, los enemigos se\n" +
                    "volveran mucho mas fuertes pero algunos soltaran\n" +
                    "objetos valiosos. Hay probabilidad de que\n" +
                    "aparezcan jefes en alguna zona aleatoria de\n" +
                    "tus alrededores, encuentralos y hazte con sus\n" +
                    "objetos. ¡MUCHA SUERTE!"));

            p.sendTitle(ChatColor.translateAlternateColorCodes('&',"&4&lHEAVY RAIN"),ChatColor.translateAlternateColorCodes('&',"&cEL CIELO HA OSCURECIDO"), 60,100,60);

            p.playSound(p, Sound.ENTITY_ENDER_DRAGON_DEATH,1,0.6F);

            p.getWorld().strikeLightning(new Location(p.getWorld(),p.getLocation().getX(),p.getLocation().getY()+100,p.getLocation().getZ()));
        }

        instance.getThreadManager().startLoop();
    }


}
