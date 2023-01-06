package tmlust.heavyrain.threads;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.utils.Utility;

import java.util.List;

public class LoopTask extends BukkitRunnable {

    private HeavyRain instance;
    private World world;
    private World world_nether;
    private World world_the_end;

    public LoopTask(HeavyRain instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        //System.out.println("Thread-Loop");

        if(world == null || world_nether == null || world_the_end == null) {
            world = Utility.findWorldConfig(instance,"heavyrain_world");
            world_nether = Utility.findWorldConfig(instance,"heavyrain_world_nether");
            world_the_end = Utility.findWorldConfig(instance,"heavyrain_world_the_end");
        }

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

    }
}
