package net.totobirdcreations.dragonheart.util.helper;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.item.dragon.DragonBlockItem;


public class NbtHelper {

    public static String getString(NbtCompound nbt, String key, String fallback) {
        return nbt.contains(key, NbtElement.STRING_TYPE)
                ? nbt.getString(key)
                : fallback;
    }

    public static int getInt(NbtCompound nbt, String key, int fallback) {
        return nbt.contains(key, NbtElement.INT_TYPE)
                ? nbt.getInt(key)
                : fallback;
    }


    public static Identifier EMPTY_TYPE = new Identifier("minecraft", "");


    public static Identifier getItemDragonType(ItemStack stack) {
        return new Identifier(getString(getItemDragonTypeCompound(stack), "type", EMPTY_TYPE.toString()));
    }

    public static void setItemDragonType(ItemStack stack, Identifier type) {
        setItemDragonType(stack, type.toString());
    }
    public static void setItemDragonType(ItemStack stack, String type) {
        getItemDragonTypeCompound(stack).putString("type", type);
    }

    public static NbtCompound getItemDragonTypeCompound(ItemStack stack) {
        return (stack.getItem() instanceof BlockItem
                ? stack.getOrCreateSubNbt("BlockEntityTag")
                : stack.getOrCreateNbt()
        );
    }

}
