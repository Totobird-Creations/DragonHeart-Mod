package net.totobirdcreations.dragonheart.recipe.util;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;


public record TypeSerializer(
    RecipeType       type,
    RecipeSerializer serializer
) {}
