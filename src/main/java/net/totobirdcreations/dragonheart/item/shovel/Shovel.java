package net.totobirdcreations.dragonheart.item.shovel;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.totobirdcreations.dragonheart.item.material.ShovelMaterial;


public class Shovel extends ShovelItem {


    public Shovel(Settings settings, ShovelMaterial material) {

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
