package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class DragonscaleItem extends DragonItem {

    public DragonscaleItem(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "scale";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {
    }

}
