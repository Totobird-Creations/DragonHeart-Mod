package net.totobirdcreations.dragonheart.damage;

import net.minecraft.entity.damage.DamageSource;
import net.totobirdcreations.dragonheart.DragonHeart;


public class DamageSources {


    @SuppressWarnings("unused")
    public static final DamageSource BREATH_FIRE      = new DragonDamageSource( DragonDamageSource.DamageType.BREATH , DragonDamageSource.DamageClass.FIRE      ) .setScaledWithDifficulty().setFire();
    @SuppressWarnings("unused")
    public static final DamageSource BREATH_ICE       = new DragonDamageSource( DragonDamageSource.DamageType.BREATH , DragonDamageSource.DamageClass.ICE       ) .setScaledWithDifficulty();
    @SuppressWarnings("unused")
    public static final DamageSource BREATH_LIGHTNING = new DragonDamageSource( DragonDamageSource.DamageType.BREATH , DragonDamageSource.DamageClass.LIGHTNING ) .setScaledWithDifficulty();

    public static final DamageSource ROAR             = new RoarDamageSource();


    public static void register() {
        DragonHeart.LOGGER.info("Registering damage sources.");
    }


}
