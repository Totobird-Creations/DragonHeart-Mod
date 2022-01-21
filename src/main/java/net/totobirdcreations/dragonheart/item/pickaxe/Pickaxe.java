package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.item.PickaxeItem;



public class Pickaxe extends PickaxeItem {


    public Pickaxe(Settings settings, PickaxeMaterial material) {

        super(material, 0, material.getAttackSpeed(), settings);

    }


}
