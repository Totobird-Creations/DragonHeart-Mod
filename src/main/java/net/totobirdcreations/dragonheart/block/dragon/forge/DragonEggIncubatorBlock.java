package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator.DragoneggIncubatorBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class DragonEggIncubatorBlock extends DragonForgeBlock {


    public DragonEggIncubatorBlock(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "egg_incubator";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_EGG_INCUBATOR.item());
        stacks.add(stack);
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragoneggIncubatorBlockEntity(pos, state);
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (! world.isClient()) {
            // If server, try to create screen.
            NamedScreenHandlerFactory factory = state.createScreenHandlerFactory(world, pos);
            if (factory != null) {
                player.openHandledScreen(factory);
                return ActionResult.CONSUME;
            }
            return ActionResult.SUCCESS;
        } else {
            // If client, do nothing.
            return ActionResult.SUCCESS;
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            // Block broken, scatter items.
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DragoneggIncubatorBlockEntity entity) {
                ItemScatterer.spawn(world, pos, entity);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DragonBlockEntities.DRAGON_EGG_INCUBATOR, DragoneggIncubatorBlockEntity::tick);
    }

}
