package tmlust.heavyrain;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Entities {

    /**
     * Spawnea un Zombie Arcano
     * @param location El lugar donde spawneara
     * @return la entidad spawneada
     */
    public static Zombie spawnZombieArcane(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        entity.addScoreboardTag("zombie_arcane");

        // Equipment
        ((Monster) entity).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999*20, 1, false, false));
        ((Monster) entity).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999*20, 2, false, false));
        entity.setGlowing(true);

        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3);

        ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3);

        ItemStack sword = new ItemStack(Material.IRON_SWORD);

        ((Zombie) entity).getEquipment().setArmorContents(new ItemStack[]{boots,null,null,helmet});
        ((Zombie) entity).getEquipment().setItemInMainHand(sword);

        ((Zombie) entity).getEquipment().setHelmetDropChance(0);
        ((Zombie) entity).getEquipment().setBootsDropChance(0);
        ((Zombie) entity).getEquipment().setItemInMainHandDropChance(0);

        return (Zombie) entity;
    }

}
