package net.totobirdcreations.dragonheart.item.dragon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class DragonBreathItem extends DragonItemImpl {


    public DragonBreathItem(Settings settings) {
        super(settings);
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {}
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {
        ItemStack stack = new ItemStack(DragonItems.DRAGON_BREATH);
        NbtHelper.setItemDragonType(stack, id.toString());
        stacks.add(stack);
    }

}
