package net.totobirdcreations.dragonheart.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;



public class DragonDamageSource extends DamageSource {


    enum DamageType {
        BREATH,
        MELEE
    }
    enum DamageClass {
        FIRE,
        ICE,
        LIGHTNING
    }


    public final DamageType  damageType;
    public final DamageClass damageClass;


    public DragonDamageSource(DamageType damageType, DamageClass damageClass) {

        super(damageType.name().toLowerCase());
        this.damageType  = damageType;
        this.damageClass = damageClass;

    }

    @Override
    public DragonDamageSource setScaledWithDifficulty() {

        super.setScaledWithDifficulty();
        return this;

    }

    @Override
    public DragonDamageSource setFire() {

        super.setFire();
        return this;

    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        LivingEntity livingEntity = entity.getPrimeAdversary();
        String       string       = "death.dragonheart." + damageType.name().toLowerCase() + "." + damageClass.name().toLowerCase();
        /*if (livingEntity != null) {
            return new TranslatableText(string + ".entity", entity.getDisplayName(), livingEntity.getDisplayName());
        }*/
        return new TranslatableText(string, entity.getDisplayName());
    }


}

