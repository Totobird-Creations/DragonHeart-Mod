package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;



public class DragonforgeAperture extends DragonforgePowerable {


    public DragonforgeAperture(Settings settings) {

        super(settings);

    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        BlockState blockState = world.getBlockState(pos);
        world.setBlockState(pos, blockState.with(DragonforgePowerable.POWERED, ! blockState.get(DragonforgePowerable.POWERED)), Block.NOTIFY_ALL);
        update(world, pos, state);
        return ActionResult.SUCCESS;

    }


    public void update(World world, BlockPos pos, BlockState state) {

        BlockPos[] blockPositions = {
                pos.add(1, 0, 0),
                pos.add(-1, 0, 0),
                pos.add(0, 0, 1),
                pos.add(0, 0, -1)
        };
        for (int i = 0; i < blockPositions.length; i++) {
            BlockPos   blockPosition = blockPositions[i];
            BlockState blockState    = world.getBlockState(blockPosition);
            for (int j = 0; j < coreBlocks.length; j ++) {
                Block coreBlock = coreBlocks[j];
                if (blockState.isOf(coreBlock)) {
                    ((DragonforgeCore) blockState.getBlock()).update(world, blockPosition);
                }
            }
        }

    }


}
