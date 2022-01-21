package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreBaseBlockEntity;
import org.jetbrains.annotations.Nullable;



public class DragonforgeCore extends BlockWithEntity implements BlockEntityProvider {


    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final BooleanProperty ACTIVE  = BooleanProperty.of("active");


    public Block[] brickBlocks    = {};
    public Block[] hatchBlocks    = {};
    public Block[] apertureBlocks = {};
    public Block[] windowBlocks   = {};
    public Block[] supportBlocks  = {};


    public DragonforgeCore(Settings settings) {

        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false).with(ACTIVE, false));

    }


    public void setDependencyBlocks(Block[] brickBlocks, Block[] hatchBlocks, Block[] apertureBlocks, Block[] windowBlocks, Block[] supportBlocks) {

        this.brickBlocks    = brickBlocks;
        this.hatchBlocks    = hatchBlocks;
        this.apertureBlocks = apertureBlocks;
        this.windowBlocks   = windowBlocks;
        this.supportBlocks  = supportBlocks;

    }


    public static int getLuminance(BlockState state) {

        if (state.get(ACTIVE)) {
            return 15;
        } else {
            return 0;
        }

    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new DragonforgeCoreBaseBlockEntity(pos, state);

    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;

    }


    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {

        return PistonBehavior.BLOCK;

    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        super.onPlaced(world, pos, state, placer, itemStack);
        update(world, pos);

    }


    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos pos, BlockState state) {

        super.onBroken(worldAccess, pos, state);
        update((World)worldAccess, pos);

    }


    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DragonforgeCoreBaseBlockEntity) {
                ItemScatterer.spawn(world, pos, (DragonforgeCoreBaseBlockEntity)blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }


    public void update(World world, BlockPos pos) {

        boolean    isValidConstruction = false;
        Block[]    north               = brickBlocks;
        Block[]    east                = brickBlocks;
        Block[]    south               = brickBlocks;
        Block[]    west                = brickBlocks;
        BlockState apertureBlockState  = null;
        boolean    shouldBePowered     = false;

        BlockState[] blockStates = {
                world.getBlockState(pos.add(1, 0, 0)),
                world.getBlockState(pos.add(-1, 0, 0)),
                world.getBlockState(pos.add(0, 0, 1)),
                world.getBlockState(pos.add(0, 0, -1))
        };
        for (int i = 0; i < blockStates.length; i++) {
            BlockState blockState = blockStates[i];
            for (int j = 0; j < apertureBlocks.length; j++) {
                Block apertureBlock = apertureBlocks[j];
                if (blockState.isOf(apertureBlock)) {
                    isValidConstruction = true;
                    apertureBlockState = blockState;
                    if (i == 0) {
                        east = apertureBlocks;
                        west = hatchBlocks;
                    } else if (i == 1) {
                        west = apertureBlocks;
                        east = hatchBlocks;
                    } else if (i == 2) {
                        south = apertureBlocks;
                        north = hatchBlocks;
                    } else if (i == 3) {
                        north = apertureBlocks;
                        south = hatchBlocks;
                    }
                }
                if (isValidConstruction) {break;}
            }
            if (isValidConstruction) {break;}
        }

        if (isValidConstruction) {
            BlockState[] supportBlockStates = {
                    world.getBlockState(pos.add(-1, -1, -1)),
                    world.getBlockState(pos.add(-1, -1, 1)),
                    world.getBlockState(pos.add(-1, 1, -1)),
                    world.getBlockState(pos.add(-1, 1, 1)),
                    world.getBlockState(pos.add(1, -1, -1)),
                    world.getBlockState(pos.add(1, -1, 1)),
                    world.getBlockState(pos.add(1, 1, -1)),
                    world.getBlockState(pos.add(1, 1, 1))
            };
            BlockState[] brickBlockStates = {
                    world.getBlockState(pos.add(-1, -1, 0)),
                    world.getBlockState(pos.add(-1, 0, -1)),
                    world.getBlockState(pos.add(-1, 0, 1)),
                    world.getBlockState(pos.add(-1, 1, 0)),
                    world.getBlockState(pos.add(0, -1, -1)),
                    world.getBlockState(pos.add(0, -1, 0)),
                    world.getBlockState(pos.add(0, -1, 1)),
                    world.getBlockState(pos.add(0, 1, -1)),
                    world.getBlockState(pos.add(0, 1, 1)),
                    world.getBlockState(pos.add(1, -1, 0)),
                    world.getBlockState(pos.add(1, 0, -1)),
                    world.getBlockState(pos.add(1, 0, 1)),
                    world.getBlockState(pos.add(1, 1, 0))
            };
            BlockState[] windowBlockStates = {
                    world.getBlockState(pos.add(0, 1, 0))
            };
            BlockState[] westBlockStates = {
                    world.getBlockState(pos.add(-1, 0, 0))
            };
            BlockState[] eastBlockStates = {
                    world.getBlockState(pos.add(1, 0, 0))
            };
            BlockState[] southBlockStates = {
                    world.getBlockState(pos.add(0, 0, 1))
            };
            BlockState[] northBlockStates = {
                    world.getBlockState(pos.add(0, 0, -1))
            };
            isValidConstruction = validate(supportBlockStates, supportBlocks) && validate(brickBlockStates, brickBlocks) && validate(windowBlockStates, windowBlocks) &&
                    validate(westBlockStates, west) && validate(eastBlockStates, east) && validate(southBlockStates, south) && validate(northBlockStates, north);
        }

        if (isValidConstruction) {
            shouldBePowered = apertureBlockState.get(DragonforgePowerable.POWERED);
        }
        if (world.getBlockState(pos).isOf(this)) {
            world.setBlockState(pos, world.getBlockState(pos).with(ACTIVE, isValidConstruction).with(POWERED, shouldBePowered), Block.NOTIFY_ALL);
        }

        BlockPos[] blockPositions = {
                pos.add(1, 0, 0),
                pos.add(-1, 0, 0),
                pos.add(0, 0, 1),
                pos.add(0, 0, -1),
                pos.add(0, 1, 0)
        };
        for (int i = 0; i < blockPositions.length; i++) {
            BlockPos   blockPosition = blockPositions[i];
            BlockState blockState    = world.getBlockState(blockPosition);
            boolean    done          = false;
            for (int j = 0; j < brickBlocks.length; j++) {
                Block block = brickBlocks[j];
                if (blockState.isOf(block)) {
                    ((DragonforgePowerable)blockState.getBlock()).update(world, blockPosition, blockState);
                    done = true;
                }
                if (done) {
                    break;
                }
            }
            if (! done) {
                for (int j = 0; j < hatchBlocks.length; j++) {
                    Block block = hatchBlocks[j];
                    if (blockState.isOf(block)) {
                        ((DragonforgePowerable) blockState.getBlock()).update(world, blockPosition, blockState);
                        done = true;
                    }
                    if (done) {
                        break;
                    }
                }
            }
            if (! done) {
                for (int j = 0; j < windowBlocks.length; j++) {
                    Block block = windowBlocks[j];
                    if (blockState.isOf(block)) {
                        ((DragonforgeWindow) blockState.getBlock()).update(world, blockPosition, blockState);
                        done = true;
                    }
                    if (done) {
                        break;
                    }
                }
            }
        }

    }


    public boolean validate(BlockState[] blockStates, Block[] blocks) {

        boolean isValid = true;
        for (int i = 0; i < blockStates.length; i++) {
            BlockState blockState = blockStates[i];
            isValid = false;
            for (int j = 0; j < blocks.length; j++) {
                Block block = blocks[j];
                if (blockState.isOf(block)) {
                    isValid = true;
                }
                if (isValid) {
                    break;
                }
            }
            if (! isValid) {
                break;
            }
        }
        return isValid;

    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        openScreen(state, world, pos, player);
        return ActionResult.SUCCESS;

    }


    public void openScreen(BlockState state, World world, BlockPos pos, PlayerEntity player) {}


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        builder.add(POWERED, ACTIVE);

    }

}
