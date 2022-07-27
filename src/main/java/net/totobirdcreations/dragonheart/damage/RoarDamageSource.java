package net.totobirdcreations.dragonheart.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.DragonHeart;


public class RoarDamageSource extends DamageSource {

    public RoarDamageSource() {
        super("roar");
    }

    @Override
    public RoarDamageSource setScaledWithDifficulty() {
        super.setScaledWithDifficulty();
        return this;
    }

    @Override
    public RoarDamageSource setFire() {
        super.setFire();
        return this;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        return Text.translatable("death." + DragonHeart.MOD_ID + ".roar", entity.getDisplayName());
    }


}

