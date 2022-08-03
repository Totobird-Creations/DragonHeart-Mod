package net.totobirdcreations.dragonheart.item.dragon;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class DragonscaleItem extends DragonItemImpl {

    public DragonscaleItem(Settings settings) {
        super(settings);
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {}
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {}

}
