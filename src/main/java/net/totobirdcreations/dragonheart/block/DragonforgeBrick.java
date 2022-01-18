package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class DragonforgeBrick extends DragonforgePowerable {

    public static final BooleanProperty VENT        = BooleanProperty.of("vent");

    public              Block           windowBlock = null;

    public DragonforgeBrick(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(VENT, false));
    }

    public void setDependencyBlocks(Block windowBlock, Block[] coreBlocks) {
        this.windowBlock = windowBlock;
        this.coreBlocks  = coreBlocks;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        update(world, pos, state);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
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
        if (shouldHaveWindow) {

            BlockEntity blockEntity = world.getBlockEntity(pos);
            Clearable.clear(blockEntity);
            world.setBlockState(pos, windowBlock.getDefaultState());
            BlockState newBlockState = world.getBlockState(pos);
            ((DragonforgeWindow)newBlockState.getBlock()).update(world, pos, newBlockState);

        } else {

            boolean shouldHaveVent = false;
            boolean shouldBePowered = false;
            for (int i = 0; i < blockStates.length; i++) {
                blockState = blockStates[i];
                for (int j = 0; j < coreBlocks.length; j++) {
                    Block coreBlock = coreBlocks[j];
                    if (blockState.isOf(coreBlock)) {
                        shouldHaveVent = true;
                        if (blockState.get(DragonforgeCore.POWERED)) {
                            shouldBePowered = true;
                            break;
                        }
                    }
                }
                if (shouldBePowered) {
                    break;
                }
            }
            world.setBlockState(pos, state.with(VENT, shouldHaveVent).with(POWERED, shouldBePowered), Block.NOTIFY_ALL);

        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VENT);
    }

}
