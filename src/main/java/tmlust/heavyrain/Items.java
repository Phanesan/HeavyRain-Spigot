package tmlust.heavyrain;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Items {

    private List<String> listItem;
    public final ItemStack OmniFire;
    public final ItemStack ShardOfFire;
    public final ItemStack WritingLife;
    public final ItemStack ArcaneBookLife;
    public final ItemStack KingSlimeCore;

    /**
     * Inicializa los items del plugin
     */
    public Items(){
        listItem = new ArrayList<>();

        OmniFire = initOmniFire();
        ShardOfFire = initShardOfFire();
        WritingLife = initWritingLife();
        ArcaneBookLife = initArcaneBookLife();
        KingSlimeCore = initKingSlimeCore();
    }

    /**
     * Consigue la lista de los items
     * @return una List con los nombres de los items
     */
    public List<String> getListItem() {
        return listItem;
    }

    private ItemStack initOmniFire() {
        ItemStack omnifire = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = omnifire.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6OmniFuego"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bUsalo para crear la &6Esquirla del Fuego"));
        meta.setLore(lore);
        omnifire.setItemMeta(meta);

        listItem.add("OmniFire");

        return omnifire;
    }

    private ItemStack initShardOfFire() {
        ItemStack shardoffire = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = shardoffire.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6Esquirla del Fuego"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bUsalo para crear el &lLibro Arcano: &6&lVida"));
        meta.setLore(lore);
        shardoffire.setItemMeta(meta);

        listItem.add("ShardOfFire");

        return shardoffire;
    }

    private ItemStack initWritingLife() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&aEscritura Arcana: &6Vida"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bUsalo para crear el &lLibro Arcano: &6&lVida"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        listItem.add("WritingLife");

        return item;
    }

    private ItemStack initKingSlimeCore() {
        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&cNucleo del Rey Slime"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bUsalo para crear el &lLibro Arcano: &6&lVida"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        listItem.add("KingSlimeCore");

        return item;
    }

    private ItemStack initArcaneBookLife() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&b&lLibro Arcano: &6&lVida"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',"&aPasiva: Persistencia"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bAl tener este libro en tu mano izquierda, tu"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bvida incrementa a tres barras de corazones"));
        meta.setLore(lore);

        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(),"generic.max_health",40, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
        item.setItemMeta(meta);

        listItem.add("ArcaneBookLife");

        return item;
    }

}
