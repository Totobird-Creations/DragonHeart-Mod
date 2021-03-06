package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;



public class DragonforgeHatch extends DragonforgePowerable {


    public DragonforgeHatch(Settings settings) {
        super(settings);
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockPos[] blockPositions = {
                pos.add(1, 0, 0),
                pos.add(-1, 0, 0),
                pos.add(0, 0, 1),
                pos.add(0, 0, -1)
        };
        BlockPos coreBlockPos = null;
        boolean    passed     = true;
        for (BlockPos blockPos : blockPositions) {
            BlockState blockState = world.getBlockState(blockPos);
            for (Block coreBlock : coreBlocks) {
                if (blockState.isOf(coreBlock)) {
                    if (coreBlockPos != null) {
                        passed = false;
                        break;
                    } else {
                        coreBlockPos = blockPos;
                    }
                }
            }
            if (!passed) {
                break;
            }
        }
        if (passed && coreBlockPos != null) {
            BlockState coreBlockState = world.getBlockState(coreBlockPos);
            ((DragonforgeCore)coreBlockState.getBlock()).openScreen(coreBlockState, world, coreBlockPos, player);
        }
        return ActionResult.SUCCESS;
    }


}
