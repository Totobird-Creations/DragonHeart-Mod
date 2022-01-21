package net.totobirdcreations.dragonheart.item.dragonevent;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.totobirdcreations.dragonheart.effect.FrozenStatusEffect;

public class Ice {

    public static void hit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.extinguish();
        target.setFrozenTicks(100);
        StatusEffectInstance effect = new StatusEffectInstance(new FrozenStatusEffect(), 500, 0, true, true, true);
        target.addStatusEffect(effect, attacker);
        target.world.playSound(
                null,
                target.getBlockPos(),
                SoundEvents.BLOCK_GLASS_BREAK,
                SoundCategory.NEUTRAL,
                1f,
                1f
        );
    }

}
