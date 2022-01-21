package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.totobirdcreations.dragonheart.effect.FrozenStatusEffect;
import net.totobirdcreations.dragonheart.item.dragonevent.Ice;

public class DragonSwordIce extends DragonSword {
    public DragonSwordIce(Settings settings, DragonSwordMaterial material) {
        super(settings, material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Ice.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
