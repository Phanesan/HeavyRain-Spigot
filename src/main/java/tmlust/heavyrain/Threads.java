package tmlust.heavyrain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tmlust.heavyrain.utilities.Utility;

import java.util.List;

public abstract class Threads {

    /**
     * Cuando se inicia la Heavy Rain, se ejecuta este metodo.
     * Todas las tareas repetitivas que se hagan durante la heavy rain se
     * realizan en este metodo
     * @param instance Instancia del main
     * @return el objeto BukkitRunnable
     */
    public static BukkitTask startLoop(HeavyRain instance) {
        return new BukkitRunnable() {
            World world = Utility.findWorldConfig(instance,"heavyrain_world");
            World world_nether = Utility.findWorldConfig(instance,"heavyrain_world_nether");
            World world_the_end = Utility.findWorldConfig(instance,"heavyrain_world_the_end");
            @Override
            public void run() {
                //System.out.println("Thread-Loop");
                world.setTime(21600);
                world.setStorm(true);
                world.setThundering(true);

                List<Player> playersInWorld = world.getPlayers();
                for(Player p : playersInWorld) {
                    p.getWorld().strikeLightning(new Location(p.getWorld(),p.getLocation().getX(),p.getLocation().getY()+100,p.getLocation().getZ()));
                }

                List<Player> playersInNether = world_nether.getPlayers();
                for(Player p : playersInNether) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,20*5,0));
                }

                List<Player> playersInEnd = world_the_end.getPlayers();
                for(Player p : playersInEnd) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,20*5,0));
                }

                // Al finalizar la heavy rain
                if(instance.getCommands().getSecondsTimer() >= 720) {
                    List<Player> listPlayers = Utility.getConfigWorldsPlayers(instance,"heavyrain_world","heavyrain_world_nether","heavyrain_world_the_end");
                    for(Player p : listPlayers) {
                        p.sendMessage(ChatColor.DARK_GREEN + "Las nubes se disipan... pero volveran");
                    }

                    instance.getCommands().setHeavyRainActivated(false);
                    Bukkit.getScheduler().cancelTask(getTaskId());
                    world.setThundering(false);
                    world.setWeatherDuration(20*80);
                    instance.getCommands().setSecondsTimer(1);
                }
            }
        }.runTaskTimer(instance,0,50);
    }
    
}
