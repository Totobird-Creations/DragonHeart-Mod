package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class DragonForgeCoreBlock extends DragonForgeBlock {


    public DragonForgeCoreBlock(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "forge_core";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_FORGE_CORE.item());
        NbtHelper.setItemDragonType(stack, NbtHelper.EMPTY_TYPE);
        stacks.add(stack);
    }
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_FORGE_CORE.item());
        NbtHelper.setItemDragonType(stack, dragon);
        stacks.add(stack);
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if (world.getBlockEntity(pos) instanceof DragonForgeCoreBlockEntity entity) {
            ItemStack stack = new ItemStack(this);
            NbtHelper.setItemDragonType(stack, entity.type);
            return stack;
        } else {
            return super.getPickStack(world, pos, state);
        }
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonForgeCoreBlockEntity(pos, state);
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (! world.isClient()) {
            // If server, open screen.
            open(state, world, pos, player);
            return ActionResult.CONSUME;
        } else {
            // If client, do nothing.
            return ActionResult.SUCCESS;
        }
    }

    public static void open(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        // Open screen.
        NamedScreenHandlerFactory factory = state.createScreenHandlerFactory(world, pos);
        if (factory != null) {
            player.openHandledScreen(factory);
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            // Block broken, scatter items.
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DragonForgeCoreBlockEntity entity) {
                ItemScatterer.spawn(world, pos, entity);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

}
