package net.totobirdcreations.dragonheart.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FrozenStatusEffect extends StatusEffect {
    public FrozenStatusEffect() {
        super(
                StatusEffectCategory.HARMFUL,
                0xbfffff
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setVelocity(0.0, -3.0, 0.0);
    }

}
