package net.totobirdcreations.dragonheart.resource.recipe.util;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;


public record TypeSerializer(
    RecipeType       type,
    RecipeSerializer serializer
) {}
