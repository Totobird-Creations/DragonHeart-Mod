package net.totobirdcreations.dragonheart.block.dragon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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

    public static BooleanProperty CAN_RESET = BooleanProperty.of("can_reset");


    public DragonGriefedBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(CAN_RESET, false)
        );
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CAN_RESET);
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
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if (state.get(CAN_RESET) && world.getBlockEntity(pos) instanceof DragonGriefedBlockEntity entity) {
            return entity.resetState.getBlock().getPickStack(world, pos, entity.resetState);
        }
        return ItemStack.EMPTY;
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(CAN_RESET)) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() == Items.GLASS_BOTTLE) {
                if (! world.isClient()) {
                    if (world.getBlockEntity(pos) instanceof DragonGriefedBlockEntity entity) {
                        stack.decrement(1);
                        ItemStack newStack = new ItemStack(DragonItems.DRAGON_BREATH);
                        newStack.getOrCreateNbt().putString("type", entity.type.toString());
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
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

}
