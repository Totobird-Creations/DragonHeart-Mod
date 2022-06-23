package net.totobirdcreations.dragonheart.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.util.FrozenEffectEntityInterface;


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
        entity.setVelocity(0.0, -3.0, 0.0);
        entity.setFrozenTicks(200);
        if (entity.isOnFire()) {
            entity.removeStatusEffect(ModStatusEffects.FROZEN);
            entity.extinguish();
        }
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        ((FrozenEffectEntityInterface)entity).setIced(true);
    }


    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        ((FrozenEffectEntityInterface)entity).setIced(false);
    }


}
