package net.totobirdcreations.dragonheart.entity.dragon;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;


public class DragonIceEntity extends DragonEntity {

    public DragonIceEntity(EntityType<? extends DragonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public DragonType getDragonType() {
        return DragonType.ICE;
    }

    @Override
    public RGBColour getDefaultEyeColour() {
        return new RGBColour(0.0f, 0.75f, 1.0f);
    }

}
