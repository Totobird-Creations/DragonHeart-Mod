package net.totobirdcreations.dragonheart.item.util;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import static java.awt.image.DataBuffer.TYPE_INT;


public interface DragonColouredItem extends DyeableItem {

    String COLOUR_KEY = "colour";


    @Override
    default boolean hasColor(ItemStack stack) {
        return true;
    }

    @Override
    default int getColor(ItemStack stack) {
        return getColour(stack).toInt();
    }
    static RGBColour getColour(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(COLOUR_KEY, TYPE_INT)) {
            return new RGBColour(nbt.getInt(COLOUR_KEY));
        } else {
            return DragonResourceLoader.getResource(NbtHelper.getItemDragonType(stack)).colourGlow();
        }
    }

    @Override
    default void removeColor(ItemStack stack) {
        removeColour(stack);
    }
    static void removeColour(ItemStack stack) {
        stack.getOrCreateNbt().remove(COLOUR_KEY);
    }

    @Override
    default void setColor(ItemStack stack, int colour) {
        setColour(stack, colour);
    }
    static void setColour(ItemStack stack, RGBColour colour) {
        setColour(stack, colour.toInt());
    }
    static void setColour(ItemStack stack, int colour) {
        stack.getOrCreateNbt().putInt(COLOUR_KEY, colour);
    }


}
