package net.totobirdcreations.dragonheart.effect;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.totobirdcreations.dragonheart.util.mixin.deafenedeffect.DeafenedEffectLivingEntityMixinInterface;


public class DeafenedStatusEffect extends StatusEffect {

    public DeafenedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x5f3f3f);
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }


    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (! entity.world.isClient()) {
            ((DeafenedEffectLivingEntityMixinInterface) entity).setDeafened(true);
        }
    }


    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (! entity.world.isClient()) {
            ((DeafenedEffectLivingEntityMixinInterface) entity).setDeafened(false);
            if (entity.getWorld().isClient()) {
                KeyBinding.unpressAll();
            }
        }
    }

}
