package net.totobirdcreations.dragonheart.entity.dragonegg;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;


public class DragoneggIceEntity extends DragoneggEntity {

    public DragoneggIceEntity(EntityType<DragoneggIceEntity> entityType, World world) {
        super(entityType, world);
    }

    public Item getDropItem() {
        return MiscItems.DRAGONEGG_ICE;
    }

    public EntityType<? extends DragonEntity> getSpawnEntity() {
        return ModEntities.DRAGON_ICE;
    }

}
