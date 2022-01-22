package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.totobirdcreations.dragonheart.item.material.SwordMaterial;


public class Sword extends SwordItem {


    public Sword(Item.Settings settings, SwordMaterial material) {

        super(material, 0, material.getAttackSpeed(), settings);

    }


}
