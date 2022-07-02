package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.totobirdcreations.dragonheart.item.material.PickaxeMaterial;


public class Pickaxe extends PickaxeItem {


    public Pickaxe(Settings settings, PickaxeMaterial material) {
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
