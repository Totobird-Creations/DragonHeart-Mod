package net.totobirdcreations.dragonheart.item.hoe;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.material.HoeMaterial;


public class Hoe extends HoeItem {


    public Hoe(Settings settings, HoeMaterial material) {
        super(material, 0, material.getAttackSpeed(), settings);
    }


    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }


    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }


}
