package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;


public class DragoneggCreativeItem extends DragoneggItem {

    public DragoneggCreativeItem(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "dragonegg_creative";
    }


    @Override
    public boolean isCreative(ItemStack stack) {return true;}


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {}

}
