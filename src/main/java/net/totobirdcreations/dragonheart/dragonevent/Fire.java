package net.totobirdcreations.dragonheart.dragonevent;

import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;


public class Fire {


    public static void hit(LivingEntity target, boolean sound) {

        target.setOnFireFor(25);
        if (sound) {
            target.world.playSound(
                    null,
                    target.getBlockPos(),
                    SoundEvents.ITEM_FIRECHARGE_USE,
                    SoundCategory.NEUTRAL,
                    1f,
                    1f
            );
        }

    }


}
