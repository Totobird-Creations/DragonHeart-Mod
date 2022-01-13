package net.totobirdcreations.dragonsarise.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonsarise.DragonsArise;

public class FrozenEffect extends StatusEffect {
    public FrozenEffect() {
        super(
                StatusEffectCategory.HARMFUL,
                0xbfffff
        );
    }

    /*@Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        DragonsArise.LOGGER.info(entity.readCustomDataFromNbt());
    }*/

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setVelocity(0.0, 0.0, 0.0);
        //entity.setHeadYaw();
    }
}
