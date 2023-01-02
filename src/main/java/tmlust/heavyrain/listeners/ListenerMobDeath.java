package tmlust.heavyrain.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.loot.LootContext;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.loottables.ZombieArcaneLoot;

import java.util.Random;

public class ListenerMobDeath implements Listener {

    private HeavyRain instance;

    public ListenerMobDeath(HeavyRain instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {

        if(e.getEntity().getScoreboardTags().contains("heavyrain-mob")) {
            switch(e.getEntityType()) {
                case ZOMBIE:
                    if(e.getEntity().getScoreboardTags().contains("zombie_arcane")) {
                        e.getDrops().addAll(new ZombieArcaneLoot(instance).populateLoot(new Random(), new LootContext.Builder(e.getEntity().getLocation())
                                .lootedEntity(e.getEntity())
                                .killer(e.getEntity().getKiller())
                                .build()));
                    }
                    break;
            }

        }

    }

}
