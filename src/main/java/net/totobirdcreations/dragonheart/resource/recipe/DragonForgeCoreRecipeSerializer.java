package net.totobirdcreations.dragonheart.resource.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.DragonHeart;

import java.util.ArrayList;
import java.util.Collection;


public class DragonForgeCoreRecipeSerializer implements RecipeSerializer<DragonForgeCoreRecipe> {

    public static final DragonForgeCoreRecipeSerializer INSTANCE = new DragonForgeCoreRecipeSerializer();


    @Override
    public DragonForgeCoreRecipe read(Identifier id, JsonObject json) {

        Identifier dragonType = new Identifier(JsonHelper.getString(json, "dragon_type"));

        int timeTicks = JsonHelper.getInt(json, "time");

        JsonArray inputs = JsonHelper.getArray(json, "ingredients");
        DefaultedList<DragonForgeCoreRecipe.CoreIngredient> ingredients = DefaultedList.of();
        boolean                                         typeSource       = false;
        for (int i = 0; i < 2; i++) {
            JsonObject                                                object          = inputs.get(i).getAsJsonObject();
            Ingredient                                                ingredient      = Ingredient.fromJson(object);
            Collection<DragonForgeCoreRecipe.CoreIngredient.Modifier> modifiers       = new ArrayList<>();
            Collection<String>                                        modifierStrings = new ArrayList<>();
            if (JsonHelper.hasArray(object, "forge")) {
                JsonArray array = JsonHelper.getArray(object, "forge");
                for (JsonElement modifierElement : array) {
                    String modifierString = modifierElement.getAsJsonPrimitive().getAsString();
                    if (modifierStrings.contains(modifierString)) {
                        DragonHeart.LOGGER.warn("Duplicate modifier `" + modifierString + "` in `" + id + "`");
                    } else {
                        modifierStrings.add(modifierString);
                        DragonForgeCoreRecipe.CoreIngredient.Modifier modifier = DragonForgeCoreRecipe.CoreIngredient.Modifier.fromString(modifierString, id);
                        if (modifier != null) {
                            if (modifier == DragonForgeCoreRecipe.CoreIngredient.Modifier.TYPE_SOURCE) {
                                if (typeSource) {
                                    DragonHeart.LOGGER.warn("Duplicate modifier `" + modifierString + "` in `" + id + "`");
                                } else {
                                    typeSource = true;
                                    modifiers.add(modifier);
                                }
                            } else {
                                modifiers.add(modifier);
                            }
                        }
                    }
                }
            }
            ingredients.add(new DragonForgeCoreRecipe.CoreIngredient(
                    ingredient,
                    modifiers
            ));
        }

        ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

        return new DragonForgeCoreRecipe(id, dragonType, timeTicks, ingredients, output);

    }


    @Override
    public DragonForgeCoreRecipe read(Identifier id, PacketByteBuf buf) {
        Identifier dragonType = new Identifier(buf.readString());
        int        timeTicks  = buf.readInt();
        ItemStack  output     = buf.readItemStack();
        DefaultedList<DragonForgeCoreRecipe.CoreIngredient> ingredients = DefaultedList.ofSize(buf.readInt(), null);
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient input = Ingredient.fromPacket(buf);
            Collection<DragonForgeCoreRecipe.CoreIngredient.Modifier> modifiers = new ArrayList<>();
            for (int j = 0; j < buf.readInt(); i++) {
                DragonForgeCoreRecipe.CoreIngredient.Modifier modifier = DragonForgeCoreRecipe.CoreIngredient.Modifier.fromString(buf.readString(), new Identifier(DragonHeart.ID, "<networkRecipeType>"));
                if (modifier != null) {
                    modifiers.add(modifier);
                }
            }
            ingredients.set(i, new DragonForgeCoreRecipe.CoreIngredient(input, modifiers));
        }
        return new DragonForgeCoreRecipe(id, dragonType, timeTicks, ingredients, output);
    }


    @Override
    public void write(PacketByteBuf buf, DragonForgeCoreRecipe recipe) {
        buf.writeString    (recipe. dragonType  .toString() );
        buf.writeInt       (recipe. timeTicks               );
        buf.writeItemStack (recipe. output                  );
        buf.writeInt       (recipe. ingredients .size()     );
        for (DragonForgeCoreRecipe.CoreIngredient ingredient : recipe.ingredients) {
            ingredient.ingredient().write(buf);
            buf.writeInt(ingredient.modifiers().size());
            for (DragonForgeCoreRecipe.CoreIngredient.Modifier modifier : ingredient.modifiers()) {
                buf.writeString(modifier.toString());
            }
        }
    }


}
