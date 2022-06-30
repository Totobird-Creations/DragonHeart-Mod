package net.totobirdcreations.dragonheart.dragonevent;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class Lightning {


    public static void hit(@Nullable ItemStack stack, LivingEntity target, LivingEntity attacker, boolean sound) {

        World world  = target.getWorld();
        // If server, strike lightning on target if it has view of the sky.
        if (! world.isClient() && world.isSkyVisible(target.getBlockPos())) {
            LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            entity.setPosition(target.getPos());
            world.spawnEntity(entity);
        }

    }


}
