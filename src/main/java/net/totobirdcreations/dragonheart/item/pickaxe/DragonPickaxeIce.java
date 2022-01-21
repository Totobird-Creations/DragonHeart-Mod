package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Ice;
import net.totobirdcreations.dragonheart.item.sword.DragonSword;
import net.totobirdcreations.dragonheart.item.sword.DragonSwordMaterial;

public class DragonPickaxeIce extends DragonPickaxe {
    public DragonPickaxeIce(Settings settings, DragonPickaxeMaterial material) {
        super(settings, material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Ice.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
