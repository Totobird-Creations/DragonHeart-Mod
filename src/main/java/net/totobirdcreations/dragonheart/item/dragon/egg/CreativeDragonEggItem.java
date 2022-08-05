package net.totobirdcreations.dragonheart.item.dragon.egg;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;


public class CreativeDragonEggItem extends DragonEggItem {

    public CreativeDragonEggItem(Item.Settings settings) {
        super(settings);
    }


    @Override
    public boolean isCreative(ItemStack stack) {return true;}


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {}

}
