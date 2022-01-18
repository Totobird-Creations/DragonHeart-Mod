package net.totobirdcreations.dragonheart.recipe;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class DragonforgeCoreTypeRecipe implements Recipe<SimpleInventory> {

    public enum RequiredForgeType {
        FIRE,
        ICE,
        LIGHTNING,
        FIRE_OR_ICE,
        FIRE_OR_LIGHTNING,
        ICE_OR_LIGHTNING,
        ANY;

        public static RequiredForgeType getFromString(String name) {
            switch (name) {
                case "fire"              : return FIRE;
                case "ice"               : return ICE;
                case "lightning"         : return LIGHTNING;
                case "fire_or_ice"       : return FIRE_OR_ICE;
                case "fire_or_lightning" : return FIRE_OR_LIGHTNING;
                case "ice_or_lightning"  : return ICE_OR_LIGHTNING;
                default                  : return ANY;
            }
        }

    }

    private final Identifier                identifier;
    private final ItemStack                 output;
    private final DefaultedList<Ingredient> ingredients;
    private final RequiredForgeType         type;

    public DragonforgeCoreTypeRecipe(Identifier identifier, ItemStack output, DefaultedList<Ingredient> ingredients, RequiredForgeType type) {
        this.identifier  = identifier;
        this.output      = output;
        this.ingredients = ingredients;
        this.type        = type;
    }

    public RequiredForgeType getRequiredForgeType() {
        return type;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return (ingredients.get(0).test(inventory.getStack(0)) && ingredients.get(1).test(inventory.getStack(1))) ||
                (ingredients.get(0).test(inventory.getStack(1)) && ingredients.get(1).test(inventory.getStack(0)));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DragonforgeCoreTypeRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return DragonforgeCoreTypeRecipeType.INSTANCE;
    }

}
