package net.totobirdcreations.dragonheart.item.armour;

import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.MiscItems;


public class EltraDragonScale extends ElytraItem {

    public EltraDragonScale(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(MiscItems.DRAGONSCALE);
    }

}
