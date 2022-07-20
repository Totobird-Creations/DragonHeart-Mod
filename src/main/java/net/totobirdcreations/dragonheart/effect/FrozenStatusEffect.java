package net.totobirdcreations.dragonheart.effect;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.totobirdcreations.dragonheart.util.mixin.frozeneffect.FrozenEffectLivingEntityMixinInterface;


public class FrozenStatusEffect extends StatusEffect {


    public FrozenStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xbfffff);
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setVelocity(0.0, -1.5, 0.0);
        entity.setFrozenTicks(150);
        if (entity.isOnFire()) {
            entity.removeStatusEffect(ModStatusEffects.FROZEN);
            entity.extinguish();
        }
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (! entity.world.isClient()) {
            ((FrozenEffectLivingEntityMixinInterface)entity).setIced(true);
        }
    }


    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (! entity.world.isClient()) {
            ((FrozenEffectLivingEntityMixinInterface)entity).setIced(false);
        } else {
            KeyBinding.unpressAll();
        }
    }


}
