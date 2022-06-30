package net.totobirdcreations.dragonheart.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;



public class ChargedStatusEffect extends StatusEffect {


    public static final double DISTANCE = 3.0;


    public ChargedStatusEffect() {

        super(StatusEffectCategory.HARMFUL, 0xbf00ff);

    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;

    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isSubmergedInWater()) {
            World  world = entity.getWorld();
            double x     = entity.getX();
            double y     = entity.getEyeY();
            double z     = entity.getZ();
            world.addParticle(
                    ParticleTypes.FLASH,
                    true,
                    x,
                    y,
                    z,
                    0.0,
                    0.0,
                    0.0
            );
            entity.kill();
            world.getOtherEntities(entity, new Box(x - DISTANCE, y - DISTANCE, z - DISTANCE, x + DISTANCE, y + DISTANCE, z + DISTANCE));
        }
    }


}
