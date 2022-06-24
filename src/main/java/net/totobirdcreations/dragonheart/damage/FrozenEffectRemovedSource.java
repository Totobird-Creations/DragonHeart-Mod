package net.totobirdcreations.dragonheart.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;


public class FrozenEffectRemovedSource extends DamageSource {

    public FrozenEffectRemovedSource() {
        super("FROZEN_EFFECT_REMOVED");
    }

    @Override
    public FrozenEffectRemovedSource setScaledWithDifficulty() {

        super.setScaledWithDifficulty();
        return this;

    }

    @Override
    public FrozenEffectRemovedSource setFire() {

        super.setFire();
        return this;

    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        return Text.translatable("death.dragonheart.frozen_removed", entity.getDisplayName());
    }


}

