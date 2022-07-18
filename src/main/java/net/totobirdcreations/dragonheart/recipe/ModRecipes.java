package net.totobirdcreations.dragonheart.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;
import net.totobirdcreations.dragonheart.mixin.BrewingRecipeRegistryMixin;
import net.totobirdcreations.dragonheart.potion.ModPotions;


public class ModRecipes {

    public static void register() {
        DragonHeart.LOGGER.info("Registering recipes.");

        registerType(
                DragonforgeCoreTypeRecipeType.IDENTIFIER,
                DragonforgeCoreTypeRecipeSerializer.INSTANCE,
                DragonforgeCoreTypeRecipeType.INSTANCE
        );

    }


    public static void registerType(String name, RecipeSerializer serializerInstance, RecipeType typeInstance) {
        Identifier identifier = new Identifier(DragonHeart.MOD_ID, name);
        Registry.register(
                Registry.RECIPE_SERIALIZER,
                identifier,
                serializerInstance
        );
        Registry.register(
                Registry.RECIPE_TYPE,
                identifier,
                typeInstance
        );
    }


    public static void registerBrewing(Potion input, Item adder, Potion output) {
        BrewingRecipeRegistryMixin.registerPotionRecipe(input, adder, output);
    }

}
