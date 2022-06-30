package net.totobirdcreations.dragonheart.entity.dragon;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;


public class DragonFireEntity extends DragonEntity {

    public DragonFireEntity(EntityType<? extends DragonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public DragonType getDragonType() {
        return DragonType.FIRE;
    }

    @Override
    public RGBColour getDefaultEyeColour() {
        return new RGBColour(1.0f, 0.5f, 0.0f);
    }

}
