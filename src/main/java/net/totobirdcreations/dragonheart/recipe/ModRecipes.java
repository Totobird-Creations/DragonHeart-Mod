package net.totobirdcreations.dragonheart.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;



public class ModRecipes {


    public static void register() {

        DragonHeart.LOGGER.info("Registering recipes.");

        Registry.register(
                Registry.RECIPE_SERIALIZER,
                new Identifier(DragonHeart.MOD_ID, DragonforgeCoreTypeRecipeSerializer.IDENTIFIER),
                DragonforgeCoreTypeRecipeSerializer.INSTANCE
        );

        Registry.register(
                Registry.RECIPE_TYPE,
                new Identifier(DragonHeart.MOD_ID, DragonforgeCoreTypeRecipeType.IDENTIFIER),
                DragonforgeCoreTypeRecipeType.INSTANCE
        );

    }


}
