package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class DragonSword extends SwordItem {

    public DragonSword(Settings settings, DragonSwordMaterial material) {
        super(DragonboneSwordMaterial.INSTANCE, 0, material.getAttackSpeed(), settings);
    }

}
