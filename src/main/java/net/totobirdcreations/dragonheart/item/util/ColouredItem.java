package net.totobirdcreations.dragonheart.item.util;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;


public interface ColouredItem extends DyeableItem {


    int DEFAULT_COLOR = RGBColour.WHITE.asInt();


    @Override
    default int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99)) {
            return nbtCompound.getInt(COLOR_KEY);
        }
        return DEFAULT_COLOR;
    }


}
