package net.totobirdcreations.dragonheart.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.recipe.DragonforgeCoreTypeRecipe.RequiredForgeType;

public class DragonforgeCoreTypeRecipeSerializer implements RecipeSerializer<DragonforgeCoreTypeRecipe> {
    public static final DragonforgeCoreTypeRecipeSerializer INSTANCE   = new DragonforgeCoreTypeRecipeSerializer();
    public static final String                              IDENTIFIER = DragonforgeCoreTypeRecipeType.IDENTIFIER;


    @Override
    public DragonforgeCoreTypeRecipe read(Identifier id, JsonObject json) {

        ItemStack                 output      = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
        String                    forgeType   = JsonHelper.getString(json, "forgetype");

        JsonArray                 ingredients = JsonHelper.getArray(json, "ingredients");
        DefaultedList<Ingredient> inputs      = DefaultedList.ofSize(2, Ingredient.EMPTY);
        for (int i = 0; i < inputs.size(); i++) {
            inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
        }

        return new DragonforgeCoreTypeRecipe(id, output, inputs, DragonforgeCoreTypeRecipe.RequiredForgeType.getFromString(forgeType));

    }


    @Override
    public DragonforgeCoreTypeRecipe read(Identifier id, PacketByteBuf buf) {

        DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
        for (int i = 0; i < inputs.size(); i++) {
            inputs.set(i, Ingredient.fromPacket(buf));
        }

        ItemStack output = buf.readItemStack();
        return new DragonforgeCoreTypeRecipe(id, output, inputs, buf.readEnumConstant(RequiredForgeType.class));

    }


    @Override
    public void write(PacketByteBuf buf, DragonforgeCoreTypeRecipe recipe) {

        buf.writeInt(recipe.getIngredients().size());
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buf);
        }

        buf.writeItemStack(recipe.getOutput());
        buf.writeEnumConstant(recipe.getRequiredForgeType());

    }


}
