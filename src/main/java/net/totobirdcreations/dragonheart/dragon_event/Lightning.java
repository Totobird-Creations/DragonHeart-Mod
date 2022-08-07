package net.totobirdcreations.dragonheart.dragon_event;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;


public class Lightning {


    public static void hit(LivingEntity target) {
        World world  = target.getWorld();
        // If server, strike lightning on target if it has view of the sky.
        if (! world.isClient() && world.isSkyVisible(target.getBlockPos())) {
            LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            entity.setPosition(target.getPos());
            world.spawnEntity(entity);
        }
    }


}
