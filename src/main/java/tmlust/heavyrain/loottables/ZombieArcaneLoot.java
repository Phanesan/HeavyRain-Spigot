package tmlust.heavyrain.loottables;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.utils.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ZombieArcaneLoot implements LootTable {

    private HeavyRain instance;

    public ZombieArcaneLoot(HeavyRain instance) {
        this.instance = instance;
    }

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {
        Collection<ItemStack> loot = new ArrayList<>();

        if(Utility.randomBoolean(12)) {
            loot.add(new ItemStack(Material.DIAMOND));
        }
        if(Utility.randomBoolean(25)) {
            loot.add(new ItemStack(Material.GOLD_INGOT, random.nextInt(1)+1));
        }
        if(Utility.randomBoolean(45)) {
            loot.add(new ItemStack(Material.IRON_INGOT, random.nextInt(2)+1));
        }

        if(Utility.randomBoolean(4.6)) {
            loot.add(instance.getItems().OmniFire);
        }

        if(Utility.randomBoolean(8)) {
            loot.add(instance.getItems().WritingLife);
        }

        return loot;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext context) {}

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(instance,"zombie_arcane_loot");
    }
}
