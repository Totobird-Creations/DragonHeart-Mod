package net.totobirdcreations.dragonheart.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;


public class DragonIceEntity extends DragonEntity {

    public DragonIceEntity(EntityType<? extends DragonEntity> entityType, World world) {
        super(entityType, world);
    }

    public DragonType getDragonType() {
        return DragonType.ICE;
    }

}
