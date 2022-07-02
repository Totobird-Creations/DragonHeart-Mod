package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.totobirdcreations.dragonheart.block.util.ItemBlock;
import org.jetbrains.annotations.Nullable;



public class DragonforgeWindow extends AbstractGlassBlock {


    public Block[]   coreBlocks;
    public ItemBlock brickBlock;


    public DragonforgeWindow(Settings settings) {
        super(settings);
    }


    public void setDependencyBlocks(ItemBlock brickBlock, Block[] coreBlocks) {
        this.brickBlock = brickBlock;
        this.coreBlocks = coreBlocks;
    }


    @SuppressWarnings("deprecation")
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        update(world, pos);
        super.onPlaced(world, pos, state, placer, itemStack);
        DragonforgeStructureBlock.updateNearby(world, pos, coreBlocks);
    }


    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {
        super.onBroken(worldAccess, pos, state);
        DragonforgeStructureBlock.updateNearby((World)worldAccess, pos, coreBlocks);
    }


    public void update(World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.add(0, -1, 0));
        boolean shouldHaveWindow = false;
        for (Block coreBlock : coreBlocks) {
            if (blockState.isOf(coreBlock)) {
                shouldHaveWindow = true;
            }
            if (shouldHaveWindow) {
                break;
            }
        }
        if (! shouldHaveWindow) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            Clearable.clear(blockEntity);
            world.setBlockState(pos, brickBlock.block.getDefaultState());
            BlockState newBlockState = world.getBlockState(pos);
            ((DragonforgeBrick)newBlockState.getBlock()).update(world, pos, newBlockState);
        }
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(brickBlock.item);
    }


}
