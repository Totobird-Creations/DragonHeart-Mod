package net.totobirdcreations.dragonheart.item.dragon.bucket;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class DragonBucketItem extends EmptyDragonBucketItem {

    public DragonBucketItem(Settings settings) {
        super(settings);
    }

    public boolean isEmpty() {
        return false;
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {}

}
