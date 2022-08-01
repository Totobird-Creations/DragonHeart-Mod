package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class DragonbreathItem extends DragonItem {


    public DragonbreathItem(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "dragonbreath";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {
        Item      item  = MiscItems.DRAGONBREATH;
        ItemStack stack = new ItemStack(item);
        DragonColouredItem.setColour(stack, resource.colourGlow());
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("dragon", id.toString());
        stacks.add(stack);
    }

}
