package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;



public class Sword extends SwordItem {


    public Sword(Item.Settings settings, SwordMaterial material) {

        super(material, 0, material.getAttackSpeed(), settings);

    }


}
