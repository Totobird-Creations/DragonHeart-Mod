package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;



public interface ColouredItem extends DyeableItem {


    public static final int DEFAULT_COLOR = 16777215;


    @Override
    default public int getColor(ItemStack stack) {

        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99)) {
            return nbtCompound.getInt(COLOR_KEY);
        }
        return DEFAULT_COLOR;

    }


}
