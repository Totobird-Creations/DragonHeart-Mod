package net.totobirdcreations.dragonheart.recipe;

import net.minecraft.recipe.RecipeType;

public class DragonforgeCoreTypeRecipeType implements RecipeType<DragonforgeCoreTypeRecipe> {

    private DragonforgeCoreTypeRecipeType() {}

    public static final DragonforgeCoreTypeRecipeType INSTANCE   = new DragonforgeCoreTypeRecipeType();
    public static final String                        IDENTIFIER = "dragonforge";

}
