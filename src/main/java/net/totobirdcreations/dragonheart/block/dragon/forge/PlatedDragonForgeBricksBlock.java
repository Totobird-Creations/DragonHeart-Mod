package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.PlatedDragonForgeBricksBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class PlatedDragonForgeBricksBlock extends DragonBlock {

    public static BooleanProperty UNBREAKABLE = BooleanProperty.of("unbreakable");


    public PlatedDragonForgeBricksBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with( Properties.FACING , Direction.UP )
                .with( UNBREAKABLE       , true         )
        );
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add( Properties.FACING );
        builder.add( UNBREAKABLE       );
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if (world.getBlockEntity(pos) instanceof PlatedDragonForgeBricksBlockEntity entity) {
            ItemStack stack = new ItemStack(this);
            NbtHelper.setItemDragonType(stack, entity.type);
            return stack;
        } else {
            return super.getPickStack(world, pos, state);
        }
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        // When placing block, face the plated side toward the player.
        return this.getDefaultState().with(Properties.FACING, context.getSide());
    }


    @Override
    public String getNameId() {
        return "plated_forge_bricks";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        ItemStack stack = new ItemStack(DragonBlocks.PLATED_DRAGON_FORGE_BRICKS.item());
        NbtHelper.setItemDragonType(stack, dragon);
        stacks.add(stack);
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlatedDragonForgeBricksBlockEntity(pos, state);
    }


    @SuppressWarnings("deprecation")
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        // Get targeted side of block.
        Vec3d          start  = player.getEyePos();
        Vec3d          end    = start.add(player.getRotationVector().multiply(player.getBlockPos().getSquaredDistance(pos) + 2.0f));
        BlockHitResult result = world.raycast(new RaycastContext(
                start, end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        ));
        // If targeted side is not plated, return default.
        if (result.getType() == HitResult.Type.BLOCK) {
            if (result.getSide() != state.get(Properties.FACING)) {
                return super.calcBlockBreakingDelta(state, player, world, pos);
            }
        }
        if (state.get(UNBREAKABLE)) {
            // Unbreakable.
            return 0.0f;
        } else {
            float f = state.getHardness(world, pos);
            if (f == -1.0F) {
                // Unbreakable.
                return 0.0F;
            } else {
                // Really slow the break.
                int i = player.canHarvest(state) ? 30 : 100;
                return player.getBlockBreakingSpeed(state) / f / (float)i * 0.5f;
            }
        }
    }

}
