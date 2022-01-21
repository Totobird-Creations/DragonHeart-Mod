package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Lightning;
import net.totobirdcreations.dragonheart.item.sword.DragonSword;
import net.totobirdcreations.dragonheart.item.sword.DragonSwordMaterial;

public class DragonPickaxeLightning extends DragonPickaxe {
    public DragonPickaxeLightning(Settings settings, DragonPickaxeMaterial material) {
        super(settings, material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Lightning.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
