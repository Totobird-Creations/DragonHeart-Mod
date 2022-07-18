package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggEntity;
import net.totobirdcreations.dragonheart.item.util.ColouredItem;


public class Dragonegg<T extends DragoneggEntity> extends Item implements ColouredItem {

    public EntityType<T> entity;

    public Dragonegg(Settings settings, EntityType<T> entity) {
        super(settings);
        this.entity = entity;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World           world = context.getWorld();
        DragoneggEntity egg   = entity.create(world);
        BlockPos        pos   = context.getBlockPos().add(context.getSide().getVector());
        egg.setPosition(Vec3d.ofBottomCenter(pos));
        egg.setYaw(context.getWorld().getRandom().nextFloat() % (float)(Math.PI * 2.0f));
        egg.setColour(getColor(context.getStack()));
        world.spawnEntity(egg);
        return ActionResult.CONSUME;
    }

}
