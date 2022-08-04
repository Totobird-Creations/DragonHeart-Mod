package net.totobirdcreations.dragonheart.item.dragon;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;


public class DragonEggCreativeItem extends DragonEggItem {

    public DragonEggCreativeItem(Settings settings) {
        super(settings);
    }


    @Override
    public boolean isCreative(ItemStack stack) {return true;}


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {}

}
