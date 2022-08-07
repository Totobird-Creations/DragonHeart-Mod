package net.totobirdcreations.dragonheart.mixin.frozen_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(LivingEntity.class)
public interface LivingEntityInterface {

    @Invoker("removeStatusEffect")
    boolean removeStatusEffect(StatusEffect type);

}
