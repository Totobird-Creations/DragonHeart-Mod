package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.totobirdcreations.dragonheart.item.material.SwordMaterial;


public class Sword extends SwordItem {


    public Sword(Item.Settings settings, SwordMaterial material) {

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
