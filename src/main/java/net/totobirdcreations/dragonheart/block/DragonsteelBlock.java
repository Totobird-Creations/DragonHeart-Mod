package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.totobirdcreations.dragonheart.gamerule.ModGamerules;

public class DragonsteelBlock extends FacingBlock {

    public DragonsteelBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(Properties.FACING, Direction.UP)
        );
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide());
    }

    @SuppressWarnings("deprecation")
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        Vec3d          start  = player.getEyePos();
        Vec3d          end    = start.add(player.getRotationVector().multiply(player.getBlockPos().getSquaredDistance(pos) + 2.0));
        BlockHitResult result = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
        if (result.getType() == HitResult.Type.BLOCK) {
            if (result.getSide() != state.get(Properties.FACING)) {
                return super.calcBlockBreakingDelta(state, player, world, pos);
            }
        }
        if (player.getWorld().getGameRules().getBoolean(ModGamerules.DRAGONSTEELBLOCK_UNBREAKABLE)) {
            return 0.0f;
        } else {
            float f = state.getHardness(world, pos);
            if (f == -1.0F) {
                return 0.0F;
            } else {
                int i = player.canHarvest(state) ? 30 : 100;
                return player.getBlockBreakingSpeed(state) / f / (float)i / 10.0f;
            }
        }
    }

}
