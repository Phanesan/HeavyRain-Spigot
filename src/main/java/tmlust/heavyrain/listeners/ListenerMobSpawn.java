package tmlust.heavyrain.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tmlust.heavyrain.Entities;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.utilities.Utility;


public class ListenerMobSpawn implements Listener {

    private HeavyRain instance;

    public ListenerMobSpawn(HeavyRain instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if(instance.getCommands().isHeavyRainActivated() && e.getEntity() instanceof Monster) {

            e.getEntity().addScoreboardTag("heavyrain-mob");

            switch(e.getEntityType()) {
                case WITHER_SKELETON:
                case STRAY:
                case ENDERMAN:
                case SKELETON:
                case DROWNED:
                case HUSK:
                case ZOMBIE_VILLAGER:
                case ZOMBIE:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 1, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 1, false, false));
                    break;

                case SPIDER:
                case CAVE_SPIDER:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 1, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 2, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999*20, 1, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 9999*20, 1, false, false));
                    break;

                case CREEPER:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999*20, 1, false, false));
                    ((Creeper) e.getEntity()).setExplosionRadius(6);
                    ((Creeper) e.getEntity()).setPowered(true);
                    break;

                case MAGMA_CUBE:
                case SLIME:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 2, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 3, false, false));
                    break;

                case WITCH:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999*20, 2, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 1, false, false));
                    break;

                case PHANTOM:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 2, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 9999*20, 1, false, false));
                    break;

                case ENDERMITE:
                case SILVERFISH:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 2, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999*20, 1, false, false));
                    break;

                case ZOMBIFIED_PIGLIN:
                case PIGLIN_BRUTE:
                case PIGLIN:
                case GHAST:
                case BLAZE:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 9999*20, 1, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 1, false, false));
                    break;

                case PILLAGER:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 1, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999*20, 1, false, false));
                    break;

                case VINDICATOR:
                case RAVAGER:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999*20, 1, false, false));
                    break;

                case VEX:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 1, false, false));
                    break;

                case SHULKER:
                case ZOGLIN:
                case HOGLIN:
                case EVOKER:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 1, false, false));
                    break;

                case GUARDIAN:
                case WITHER:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 2, false, false));
                    break;

                case ELDER_GUARDIAN:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 3, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 1, false, false));
                    break;

                case WARDEN:
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999*20, 1, false, false));
                    ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 1, false, false));
                    break;
            }

            switch(e.getEntityType()) {
                case ZOMBIE:
                    if(Utility.randomBoolean(40)) {
                        e.setCancelled(true);
                        Entities.spawnZombieArcane(e.getLocation());
                    }
                    break;
            }
        }
    }

}
