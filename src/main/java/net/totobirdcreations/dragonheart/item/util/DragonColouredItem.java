package net.totobirdcreations.dragonheart.item.util;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import org.w3c.dom.css.RGBColor;

import static java.awt.image.DataBuffer.TYPE_INT;


public interface DragonColouredItem extends DyeableItem {

    String    DATA_COLOUR_KEY = "Colour";
    String    DISPLAY_KEY     = "display";
    String    DRAGON_KEY      = "dragon";
    String    COLOUR_KEY      = "colour";
    RGBColour DEFAULT_COLOR   = RGBColour.WHITE;


    static NbtCompound getDragonNbt(ItemStack stack) {
        NbtCompound display = stack.getOrCreateSubNbt(DISPLAY_KEY);
        NbtCompound dragon  = display.getCompound(DRAGON_KEY);
        if (dragon == null) {
            dragon = new NbtCompound();
            display.put(DRAGON_KEY, dragon);
        }
        return dragon;
    }


    @Override
    default boolean hasColor(ItemStack stack) {
        return true;
    }

    @Override
    default int getColor(ItemStack stack) {
        return getColour(stack).asInt();
    }
    static RGBColour getColour(ItemStack stack) {
        NbtCompound nbt;
        nbt = getDragonNbt(stack);
        if (nbt.contains(COLOUR_KEY, TYPE_INT)) {
            return new RGBColour(nbt.getInt(COLOUR_KEY));
        }
        nbt = stack.getOrCreateNbt();
        if (nbt.contains(DATA_COLOUR_KEY, TYPE_INT)) {
            return new RGBColour(nbt.getInt(DATA_COLOUR_KEY));
        }
        return DEFAULT_COLOR;
    }

    @Override
    default void removeColor(ItemStack stack) {
        removeColour(stack);
    }
    static void removeColour(ItemStack stack) {
        NbtCompound nbt;
        nbt = getDragonNbt(stack);
        if (nbt.contains(COLOUR_KEY)) {
            nbt.remove(COLOUR_KEY);
        }
        nbt = stack.getOrCreateNbt();
        if (nbt.contains(DATA_COLOUR_KEY)) {
            nbt.remove(DATA_COLOUR_KEY);
        }
    }

    @Override
    default void setColor(ItemStack stack, int colour) {
        setColour(stack, colour);
    }
    static void setColour(ItemStack stack, RGBColour colour) {
        setColour(stack, colour.asInt());
    }
    static void setColour(ItemStack stack, int colour) {
        getDragonNbt(stack).putInt(COLOUR_KEY, colour);
        stack.getOrCreateNbt().putInt(DATA_COLOUR_KEY, colour);
    }


}
