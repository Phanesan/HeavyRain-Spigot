package tmlust.heavyrain;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

    private HeavyRain instance;

    /**
     * Inicializa las recetas
     * @param instance Instancia del plugin
     */
    public Recipes(HeavyRain instance) {
        this.instance = instance;
        Bukkit.addRecipe(initCraftShardOfFire());
        Bukkit.addRecipe(initCraftArcaneBookLife());
    }

    private ShapedRecipe initCraftShardOfFire() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(instance,"shard_of_fire"),instance.getItems().ShardOfFire);

        recipe.shape("OAO","AAA","OAO");
        recipe.setIngredient('O', new RecipeChoice.ExactChoice(instance.getItems().OmniFire));
        recipe.setIngredient('A',Material.AMETHYST_SHARD);

        return recipe;
    }

    private ShapedRecipe initCraftArcaneBookLife() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(instance,"arcane_book_life"),instance.getItems().ArcaneBookLife);

        recipe.shape("LVF","VVD","FDC");
        recipe.setIngredient('L',Material.LEATHER);
        recipe.setIngredient('V',new RecipeChoice.ExactChoice(instance.getItems().WritingLife));
        recipe.setIngredient('F',new RecipeChoice.ExactChoice(instance.getItems().ShardOfFire));
        recipe.setIngredient('C',new RecipeChoice.ExactChoice(instance.getItems().KingSlimeCore));
        recipe.setIngredient('D',Material.DIAMOND);

        return recipe;
    }
}
