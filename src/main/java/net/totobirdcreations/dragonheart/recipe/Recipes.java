package net.totobirdcreations.dragonheart.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.recipe.util.TypeSerializer;


public class Recipes {


    public static TypeSerializer DRAGON_FORGE_CORE = registerRecipe(
            DragonForgeCoreRecipeType.ID,
            DragonForgeCoreRecipeSerializer.INSTANCE,
            DragonForgeCoreRecipeType.INSTANCE
    );



    public static TypeSerializer registerRecipe(String name, RecipeSerializer serializerInstance, RecipeType typeInstance) {
        Identifier identifier = new Identifier(DragonHeart.ID, name);
        return new TypeSerializer(
                Registry.register(
                        Registry.RECIPE_TYPE,
                        identifier,
                        typeInstance
                ),
                Registry.register(
                        Registry.RECIPE_SERIALIZER,
                        identifier,
                        serializerInstance
                )
        );
    }


    public static void register() {
        DragonHeart.LOGGER.info("Registering recipes.");
    }

}
