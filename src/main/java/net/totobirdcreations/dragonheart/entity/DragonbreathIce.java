package net.totobirdcreations.dragonheart.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.DragonforgeBlocks;
import net.totobirdcreations.dragonheart.block.ModBlockTags;
import net.totobirdcreations.dragonheart.block.ModBlocks;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeApertureBlockEntity;
import net.totobirdcreations.dragonheart.damage.ModDamageSources;
import net.totobirdcreations.dragonheart.item.HiddenItems;
import net.totobirdcreations.dragonheart.dragonevent.Ice;



public class DragonbreathIce extends Dragonbreath {


    public Block griefBlock = ModBlocks.FROZEN.block;


    public DragonbreathIce(EntityType entityType, World world) {

        super(entityType, world);

    }


    public DragonbreathIce(World world) {

        super(ModEntities.DRAGONBREATH_ICE_TYPE, world);

    }


    public DragonbreathIce(World world, LivingEntity owner) {

        super(ModEntities.DRAGONBREATH_ICE_TYPE, world, owner);

    }


    public DragonbreathIce(double x, double y, double z, World world) {

        super(ModEntities.DRAGONBREATH_ICE_TYPE, x, y,z, world);

    }


    @Override
    public void tick() {

        super.tick();

        if (ModBlockTags.DRAGON_ICEABLE.contains(getWorld().getBlockState(getBlockPos()).getBlock())) {
            world.setBlockState(getBlockPos(), Blocks.PACKED_ICE.getDefaultState());
            kill();
        }

    }


    @Override
    public Item getDefaultItem() {

        return HiddenItems.DRAGONBREATH_ICE;

    }


    @Override
    public void onEntityHit(EntityHitResult hitResult) {

        super.onEntityHit(hitResult);

        if (! world.isClient()) {
            Entity entity = hitResult.getEntity();
            if (entity instanceof LivingEntity) {
                Ice.hit(null, (LivingEntity) entity, (LivingEntity) getOwner(), false);
                entity.damage(ModDamageSources.BREATH_ICE, 10.0f);
            }

            kill();
        }

    }


    @Override
    public void onBlockHit(BlockHitResult hitResult) {

        super.onBlockHit(hitResult);

        if (! world.isClient()) {
            world.sendEntityStatus(this, (byte)3);
            BlockPos pos   = hitResult.getBlockPos();

            if (! ModBlockTags.DRAGON_UNGRIEFABLE.contains(world.getBlockState(pos).getBlock())) {
                world.setBlockState(pos, griefBlock.getDefaultState());
            }

            powerApertureIfFound(world, pos, world.getBlockState(pos));

            kill();
        }

    }


    @Override
    public void powerApertureIfFound(World world, BlockPos pos, BlockState state) {

        if (state.isOf(DragonforgeBlocks.DRAGONFORGE_APERTURE_ICE.block)) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof DragonforgeApertureBlockEntity) {
                ((DragonforgeApertureBlockEntity) entity).breathedOn();
            }
        }
        super.powerApertureIfFound(world, pos, state);

    }


}
