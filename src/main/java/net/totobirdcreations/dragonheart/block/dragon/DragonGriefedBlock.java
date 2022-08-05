package net.totobirdcreations.dragonheart.block.dragon;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonGriefedBlockEntity;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class DragonGriefedBlock extends DragonBlock {


    public DragonGriefedBlock(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "griefed";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {}


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonGriefedBlockEntity(pos, state);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }


    @Override
    public void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        world.syncWorldEvent(player, 2001, pos, getRawIdFromState(Blocks.OBSIDIAN.getDefaultState()));
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DragonBlockEntities.DRAGON_GRIEFED, DragonGriefedBlockEntity::tick);
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == Items.GLASS_BOTTLE) {
            if (! world.isClient()) {
                if (world.getBlockEntity(pos) instanceof DragonGriefedBlockEntity entity) {
                    stack.decrement(1);
                    ItemStack newStack = new ItemStack(DragonItems.DRAGON_BREATH);
                    newStack.getOrCreateNbt().putString("dragon", entity.dragon.toString());
                    if (stack.isEmpty()) {
                        player.setStackInHand(hand, newStack);
                    } else if (! player.getInventory().insertStack(newStack)) {
                        player.dropItem(newStack, false);
                    }
                    entity.resetTime = 0;
                }
                return ActionResult.CONSUME;
            } else {
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

}
