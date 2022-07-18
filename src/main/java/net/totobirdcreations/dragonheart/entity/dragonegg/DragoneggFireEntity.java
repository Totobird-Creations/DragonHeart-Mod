package net.totobirdcreations.dragonheart.entity.dragonegg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;


public class DragoneggFireEntity extends DragoneggEntity {

    public DragoneggFireEntity(EntityType<? extends DragoneggEntity> entityType, World world) {
        super(entityType, world);
    }

    public Item getDropItem() {
        return MiscItems.DRAGONEGG_FIRE;
    }

    public EntityType getSpawnEntity() {
        return ModEntities.DRAGON_FIRE;
    }

}
