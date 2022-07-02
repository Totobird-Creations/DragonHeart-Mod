package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeApertureBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import static net.totobirdcreations.dragonheart.block.DragonforgeStructureBlock.updateNearby;



public class DragonforgeAperture extends BlockWithEntity implements BlockEntityProvider {


    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public Block[] coreBlocks;


    public DragonforgeAperture(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
    }


    public void setDependencyBlocks(Block[] coreBlocks) {
        this.coreBlocks = coreBlocks;
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonforgeApertureBlockEntity(pos, state);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
    }


    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {
        super.onBroken(worldAccess, pos, state);
        updateNearby((World)worldAccess, pos, coreBlocks);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.DRAGONFORGE_APERTURE, DragonforgeApertureBlockEntity::tick);
    }



    public void update(World world, BlockPos pos) {
        BlockPos[] blockPositions = {
                pos.add(1, 0, 0),
                pos.add(-1, 0, 0),
                pos.add(0, 0, 1),
                pos.add(0, 0, -1)
        };
        for (BlockPos blockPosition : blockPositions) {
            BlockState blockState = world.getBlockState(blockPosition);
            for (Block coreBlock : coreBlocks) {
                if (blockState.isOf(coreBlock)) {
                    ((DragonforgeCore) blockState.getBlock()).update(world, blockPosition);
                }
            }
        }
    }


}
