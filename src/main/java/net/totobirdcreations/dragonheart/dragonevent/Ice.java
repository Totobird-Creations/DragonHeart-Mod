package net.totobirdcreations.dragonheart.dragonevent;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;


public class Ice {


    public static void hit(LivingEntity target, LivingEntity attacker, boolean sound) {
        target.extinguish();
        target.setFrozenTicks(100);
        StatusEffectInstance effect = new StatusEffectInstance(ModStatusEffects.FROZEN, 500, 0, false, false, true);
        target.addStatusEffect(effect, attacker);
        if (sound) {
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


}
