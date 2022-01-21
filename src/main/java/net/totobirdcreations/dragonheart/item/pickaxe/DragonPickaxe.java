package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.totobirdcreations.dragonheart.item.sword.DragonSwordMaterial;
import net.totobirdcreations.dragonheart.item.sword.DragonboneSwordMaterial;

public class DragonPickaxe extends PickaxeItem {

    public DragonPickaxe(Settings settings, DragonPickaxeMaterial material) {
        super(DragonboneSwordMaterial.INSTANCE, 0, material.getAttackSpeed(), settings);
    }

}
