package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;



public class DragonforgePowerable extends DragonforgeStructureBlock {


    public static final BooleanProperty POWERED = BooleanProperty.of("powered");


    public DragonforgePowerable(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        update(world, pos, state);
        super.onPlaced(world, pos, state, placer, itemStack);
    }


    public void update(World world, BlockPos pos, BlockState state) {
        BlockState[] blockStates = {
                world.getBlockState(pos.add(1, 0, 0)),
                world.getBlockState(pos.add(-1, 0, 0)),
                world.getBlockState(pos.add(0, 0, 1)),
                world.getBlockState(pos.add(0, 0, -1))
        };
        boolean shouldBePowered = false;
        for (BlockState blockState : blockStates) {
            for (Block coreBlock : coreBlocks) {
                if (blockState.isOf(coreBlock)) {
                    if (blockState.get(DragonforgeCore.POWERED)) {
                        shouldBePowered = true;
                        break;
                    }
                }
            }
        }
        world.setBlockState(pos, state.with(POWERED, shouldBePowered), Block.NOTIFY_ALL);
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }


}
