package net.totobirdcreations.dragonheart.item.dragonevent;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.effect.FrozenStatusEffect;

public class Lightning {

    public static void hit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world  = target.getWorld();
        // If server, strike lightning on target if it has view of the sky.
        if (! world.isClient() && world.isSkyVisible(target.getBlockPos())) {
            LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            entity.setPosition(target.getPos());
            world.spawnEntity(entity);
        }
    }

}
