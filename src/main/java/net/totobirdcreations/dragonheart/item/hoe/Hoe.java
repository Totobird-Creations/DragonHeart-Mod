package net.totobirdcreations.dragonheart.item.hoe;

import net.minecraft.item.HoeItem;
import net.totobirdcreations.dragonheart.item.material.HoeMaterial;


public class Hoe extends HoeItem {


    public Hoe(Settings settings, HoeMaterial material) {

        super(material, 0, material.getAttackSpeed(), settings);

    }


}
