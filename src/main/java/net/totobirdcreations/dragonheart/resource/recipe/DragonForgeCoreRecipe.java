package net.totobirdcreations.dragonheart.resource.recipe;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class DragonForgeCoreRecipe implements Recipe<SimpleInventory> {

    public final Identifier                    id;
    public final Identifier                    dragonType;
    public final int                           timeTicks;
    public final DefaultedList<CoreIngredient> ingredients;
    public final ItemStack                     output;


    public DragonForgeCoreRecipe(Identifier id, Identifier dragonType, int timeTicks, DefaultedList<CoreIngredient> ingredients, ItemStack output) {
        this.id          = id;
        this.dragonType  = dragonType;
        this.timeTicks   = timeTicks;
        this.ingredients = ingredients;
        this.output      = output;
    }


    public int getTypeSource() {
        for (int i = 0; i < this.ingredients.size(); i++) {
            CoreIngredient ingredient = this.ingredients.get(i);
            if (ingredient.modifiers.contains(CoreIngredient.Modifier.TYPE_SOURCE)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {return false;}
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (! ingredients.get(i).ingredient.test(inventory.getStack(i))) {
                return false;
            }
        }
        return true;
    }
    public boolean matches(SimpleInventory inventory, DragonForgeCoreBlockEntity entity) {
        for (int i = 0; i < this.ingredients.size(); i++) {
            CoreIngredient ingredient = ingredients.get(i);
            ItemStack      stack      = inventory.getStack(i);
            if (! ingredient.ingredient.test(stack)) {
                return false;
            }
            if (ingredient.modifiers.contains(CoreIngredient.Modifier.MATCH_TYPE)
                    && ! entity.dragon.equals(NbtHelper.getItemDragonType(stack))
            ) {
                return false;
            }
        }
        return true;
    }


    @Override
    public ItemStack craft(SimpleInventory inventory) {
        int i = this.getTypeSource();
        if (i != -1) {
            ItemStack stack  = inventory.getStack(i);
            ItemStack result = this.output.copy();
            NbtHelper.setItemDragonType(result, NbtHelper.getItemDragonType(stack));
            return result;
        }
        return this.output.copy();
    }


    @Override
    public boolean fits(int width, int height) {
        return true;
    }


    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }


    @Override
    public Identifier getId() {
        return this.id;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return DragonForgeCoreRecipeSerializer.INSTANCE;
    }


    @Override
    public RecipeType<?> getType() {
        return DragonForgeCoreRecipeType.INSTANCE;
    }


    public record CoreIngredient(
            Ingredient          ingredient,
            ArrayList<Modifier> modifiers
    ) {
        public enum Modifier {
            MATCH_TYPE,
            TYPE_SOURCE;

            @Nullable
            public static Modifier fromString(String string, Identifier id) {
                switch (string) {
                    case    "match_type"  -> {return MATCH_TYPE;}
                    case    "type_source" -> {return TYPE_SOURCE;}
                    default               -> {
                        DragonHeart.LOGGER.warn("Invalid modifier `" + string + "` in `" + id + "`");
                        return null;
                    }
                }
            }
            public String toString() {
                return switch (this) {
                    case MATCH_TYPE  -> "match_type";
                    case TYPE_SOURCE -> "type_source";
                };
            }
        }
    }

}
