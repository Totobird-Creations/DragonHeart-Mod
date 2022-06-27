package net.totobirdcreations.dragonheart.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;


public class DragonLightningEntity extends DragonEntity {

    public DragonLightningEntity(EntityType<? extends DragonEntity> entityType, World world) {
        super(entityType, world);
    }

    public DragonType getDragonType() {
        return DragonType.LIGHTNING;
    }

}
