package net.totobirdcreations.dragonheart.item.dragon.bucket;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.Entities;
import org.jetbrains.annotations.Nullable;

public class DragonEggDragonBucketItem extends DragonBucketItem {

    public DragonEggDragonBucketItem(Settings settings) {
        super(settings);
    }

    public boolean isEmpty() {
        return false;
    }

    @Override
    @Nullable
    public Entity createEntity(World world) {return Entities.DRAGON_EGG.create(world);}


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {}

}
