package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggEntity;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;


public class DragoneggCreative<D extends DragonEntity, T extends DragoneggEntity<D>> extends Dragonegg<D, T> {

    public Item targetConversion;


    public DragoneggCreative(Settings settings, EntityType<T> entity, DragonEntity.DragonType type, Item targetConversion) {
        super(settings, entity, type);
        this.targetConversion = targetConversion;
    }


    @Override
    public boolean isCreative(ItemStack stack) {return true;}


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {}

}
