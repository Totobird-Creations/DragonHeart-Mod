package net.totobirdcreations.dragonheart.item.dragonevent;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class Fire {

    public static void hit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(25);
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
