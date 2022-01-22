package net.totobirdcreations.dragonheart.item.shovel;

import net.minecraft.item.ShovelItem;
import net.totobirdcreations.dragonheart.item.material.ShovelMaterial;


public class Shovel extends ShovelItem {


    public Shovel(Settings settings, ShovelMaterial material) {

        super(material, 0, material.getAttackSpeed(), settings);

    }


}
