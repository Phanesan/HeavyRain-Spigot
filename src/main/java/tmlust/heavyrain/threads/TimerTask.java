package tmlust.heavyrain.threads;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.tasks.HeavyRainTask;
import tmlust.heavyrain.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import static tmlust.heavyrain.utils.Utility.findWorldConfig;
import static tmlust.heavyrain.utils.Utility.getConfigWorldsPlayers;

public class TimerTask extends BukkitRunnable {

    private HeavyRain instance;

    public TimerTask(HeavyRain instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        List<Player> players = new ArrayList<>();

        instance.getData().addSecondsTimer(1);

        if (!instance.getData().isHeavyRainActivated()) {

            if ((instance.getData().getSecondsTimer() >= instance.getData().getSecondsMax())) {
                new HeavyRainTask(instance).runTask(instance);
                instance.getData().setSecondsTimer(1);
            } else if (instance.getData().getSecondsMax() - instance.getData().getSecondsTimer() == 3600) {

                players.addAll(getConfigWorldsPlayers(instance, "heavyrain_world", "heavyrain_world_nether", "heavyrain_world_the_end"));

                for (Player p : players) {
                    p.sendMessage(ChatColor.DARK_GREEN + "La Heavy Rain se activara en una hora.");
                    p.playSound(p, Sound.ENTITY_SKELETON_HORSE_DEATH, 1, 0.5F);
                }

            } else if (instance.getData().getSecondsMax() - instance.getData().getSecondsTimer() == 600) {

                players.addAll(getConfigWorldsPlayers(instance, "heavyrain_world", "heavyrain_world_nether", "heavyrain_world_the_end"));

                for (Player p : players) {
                    p.sendMessage(ChatColor.DARK_GREEN + "La Heavy Rain se activara en 10 minutos");
                    p.playSound(p, Sound.ENTITY_SKELETON_HORSE_DEATH, 1, 0.5F);
                }

            } else if (instance.getData().getSecondsMax() - instance.getData().getSecondsTimer() == 300) {

                players.addAll(getConfigWorldsPlayers(instance, "heavyrain_world", "heavyrain_world_nether", "heavyrain_world_the_end"));

                for (Player p : players) {
                    p.sendMessage(ChatColor.YELLOW + "La Heavy Rain se activara en 5 minutos.");
                    p.playSound(p, Sound.ENTITY_SKELETON_HORSE_DEATH, 1, 0.5F);
                }

                World world = findWorldConfig(instance, "heavyrain_world");

                if (world != null) {
                    try {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                //System.out.println("Thread-preHR");

                                if(!world.hasStorm())
                                    world.setStorm(true);
                                if(world.isThundering())
                                    world.setThundering(false);

                                if(instance.getData().isHeavyRainActivated())
                                    cancel();
                            }
                        }.runTaskTimer(instance,0,20);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else
                    instance.getLogger().warning("Algo salio mal y no se aplico la lluvia");

            } else if (instance.getData().getSecondsMax() - instance.getData().getSecondsTimer() == 60) {

                players.addAll(getConfigWorldsPlayers(instance, "heavyrain_world", "heavyrain_world_nether", "heavyrain_world_the_end"));

                for (Player p : players) {
                    p.sendMessage(ChatColor.DARK_RED + "La Heavy Rain se activara en un minuto (PREPARATE!).");
                    p.playSound(p, Sound.ENTITY_SKELETON_HORSE_DEATH, 1, 0.7F);
                }

            } else if (instance.getData().getSecondsMax() - instance.getData().getSecondsTimer() <= 10) {

                players.addAll(getConfigWorldsPlayers(instance, "heavyrain_world", "heavyrain_world_nether", "heavyrain_world_the_end"));

                for (Player p : players) {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + (instance.getData().getSecondsMax() - instance.getData().getSecondsTimer()));
                    p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0.5F);
                }

            }
        } else {
            if(instance.getData().getSecondsTimer() >= 720) {

                List<Player> listPlayers = Utility.getConfigWorldsPlayers(instance,"heavyrain_world","heavyrain_world_nether","heavyrain_world_the_end");
                for(Player p : listPlayers) {
                    p.sendMessage(ChatColor.DARK_GREEN + "Las nubes se disipan... pero volveran");
                }

                instance.getData().setHeavyRainActivated(false);

                World world = Utility.findWorldConfig(instance,"heavyrain_world");
                world.setThundering(false);
                world.setWeatherDuration(20*80);

                instance.getData().setSecondsTimer(1);
                instance.getThreadManager().stopLoop();

            }
        }
    }
}
