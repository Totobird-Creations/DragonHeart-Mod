package net.totobirdcreations.dragonheart.item.axe;

import net.minecraft.item.AxeItem;
import net.totobirdcreations.dragonheart.item.material.AxeMaterial;


public class Axe extends AxeItem {


    public Axe(Settings settings, AxeMaterial material) {

        super(material, 0, material.getAttackSpeed(), settings);

    }


}
