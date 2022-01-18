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

    protected DragonforgeWindow(Settings settings) {
        super(settings);
    }

    public void setDependencyBlocks(ItemBlock brickBlock, Block[] coreBlocks) {
        this.brickBlock = brickBlock;
        this.coreBlocks = coreBlocks;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        update(world, pos, state);
        super.onPlaced(world, pos, state, placer, itemStack);
        DragonforgeStructureBlock.updateNearby(world, pos, state, coreBlocks);
    }

    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {
        super.onBroken(worldAccess, pos, state);
        DragonforgeStructureBlock.updateNearby((World)worldAccess, pos, state, coreBlocks);
    }

    public void update(World world, BlockPos pos, BlockState state) {
        BlockState[] blockStates = {
                world.getBlockState(pos.add(1, 0, 0)),
                world.getBlockState(pos.add(-1, 0, 0)),
                world.getBlockState(pos.add(0, 0, 1)),
                world.getBlockState(pos.add(0, 0, -1))
        };
        BlockState blockState = world.getBlockState(pos.add(0, -1, 0));
        boolean shouldHaveWindow = false;
        for (int i = 0; i < coreBlocks.length; i++) {
            Block coreBlock = coreBlocks[i];
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
