package net.totobirdcreations.dragonheart.screenhandler.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;


public class FireChargeSlot extends Slot {


    public FireChargeSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.FIRE_CHARGE;
    }


}
