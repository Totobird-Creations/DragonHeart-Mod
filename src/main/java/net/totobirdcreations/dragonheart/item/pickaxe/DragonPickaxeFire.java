package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Fire;
import net.totobirdcreations.dragonheart.item.sword.DragonSword;
import net.totobirdcreations.dragonheart.item.sword.DragonSwordMaterial;

public class DragonPickaxeFire extends DragonPickaxe {
    public DragonPickaxeFire(Settings settings, DragonPickaxeMaterial material) {
        super(settings, material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Fire.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
