package net.totobirdcreations.dragonheart.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class DragonforgeStructureBlock extends Dragonforge {

    public Block[] coreBlocks;

    public DragonforgeStructureBlock(Settings settings) {
        super(settings);
    }

    public void setDependencyBlocks(Block[] coreBlocks) {
        this.coreBlocks = coreBlocks;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        updateNearby(world, pos, state, coreBlocks);
    }

    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {
        super.onBroken(worldAccess, pos, state);
        updateNearby((World)worldAccess, pos, state, coreBlocks);
    }

    public static void updateNearby(World world, BlockPos pos, BlockState state, Block[] coreBlocks) {
        BlockPos[] blockPositions = {
                pos.add(-1, -1, -1),
                pos.add(-1, -1, 0),
                pos.add(-1, -1, 1),
                pos.add(-1, 0, -1),
                pos.add(-1, 0, 0),
                pos.add(-1, 0, 1),
                pos.add(-1, 1, -1),
                pos.add(-1, 1, 0),
                pos.add(-1, 1, 1),
                pos.add(0, -1, -1),
                pos.add(0, -1, 0),
                pos.add(0, -1, 1),
                pos.add(0, 0, -1),
                pos.add(0, 0, 0),
                pos.add(0, 0, 1),
                pos.add(0, 1, -1),
                pos.add(0, 1, 0),
                pos.add(0, 1, 1),
                pos.add(1, -1, -1),
                pos.add(1, -1, 0),
                pos.add(1, -1, 1),
                pos.add(1, 0, -1),
                pos.add(1, 0, 0),
                pos.add(1, 0, 1),
                pos.add(1, 1, -1),
                pos.add(1, 1, 0),
                pos.add(1, 1, 1)
        };
        for (int i = 0; i < blockPositions.length; i++) {
            BlockPos   blockPosition = blockPositions[i];
            BlockState blockState    = world.getBlockState(blockPosition);
            for (int j = 0; j < coreBlocks.length; j++) {
                Block coreBlock = coreBlocks[j];
                if (blockState.isOf(coreBlock)) {
                    ((DragonforgeCore) blockState.getBlock()).update(world, blockPosition);
                    break;
                }
            }
        }
    }

}
