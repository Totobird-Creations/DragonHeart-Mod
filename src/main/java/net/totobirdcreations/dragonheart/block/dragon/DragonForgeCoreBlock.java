package net.totobirdcreations.dragonheart.block.dragon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class DragonForgeCoreBlock extends DragonForgeBlock {


    public DragonForgeCoreBlock(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "forge_core";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        Item item  = DragonBlocks.DRAGON_FORGE_CORE.item();
        ItemStack   stack = new ItemStack(item);
        NbtCompound nbt;
        nbt = stack.getOrCreateSubNbt("BlockEntityTag");
        nbt.putString("dragon", dragon.toString());
        stacks.add(stack);
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonForgeCoreBlockEntity(pos, state);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DragonBlockEntities.DRAGON_FORGE_CORE, DragonForgeCoreBlockEntity::tick);
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        } else {
            return open(state, world, pos, player);
        }
    }

    public static ActionResult open(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        NamedScreenHandlerFactory factory = state.createScreenHandlerFactory(world, pos);
        if (factory != null) {
            player.openHandledScreen(factory);
            return ActionResult.CONSUME;
        }
        return ActionResult.FAIL;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DragonForgeCoreBlockEntity entity) {
                ItemScatterer.spawn(world, pos, entity);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

}
