package net.totobirdcreations.dragonsarise.item.sword;

import net.minecraft.item.SwordItem;

public class DragonboneSwordItem extends SwordItem {
    public DragonboneSwordItem(Settings settings) {
        super(DragonboneSwordItemMaterial.INSTANCE, 0, DragonboneSwordItemMaterial.INSTANCE.getAttackSpeed(), settings);
    }
}
