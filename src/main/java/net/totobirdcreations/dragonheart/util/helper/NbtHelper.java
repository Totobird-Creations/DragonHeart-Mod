package net.totobirdcreations.dragonheart.util.helper;

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

    public static Identifier getBlockItemDragonType(NbtCompound nbt) {
        String value = EMPTY_TYPE.toString();
        if (nbt.get("BlockEntityTag") instanceof NbtCompound tagNbt) {
            value = getString(tagNbt, "dragon", value);
        }
        return new Identifier(value);
    }

    public static Identifier getItemDragonType(NbtCompound nbt) {
        return new Identifier(getString(nbt, "dragon", EMPTY_TYPE.toString()));
    }

    public static Identifier getItemStackDragonType(ItemStack stack) {
        return stack.getItem() instanceof DragonBlockItem
                ? getBlockItemDragonType(stack.getOrCreateNbt())
                : getItemDragonType(stack.getOrCreateNbt());
    }

}
