package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.DragonForgeBricksBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class DragonForgeBricksBlock extends DragonForgeBlock {

    public static final BooleanProperty WINDOW = BooleanProperty.of("window");
    public static final BooleanProperty VENT   = BooleanProperty.of("vent");


    @Override
    public String getNameId() {
        return "forge_bricks";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_FORGE_BRICKS.item());
        NbtHelper.setItemDragonType(stack, NbtHelper.EMPTY_TYPE);
        stacks.add(stack);
    }
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_FORGE_BRICKS.item());
        NbtHelper.setItemDragonType(stack, dragon);
        stacks.add(stack);
    }


    public DragonForgeBricksBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with( WINDOW , false)
                .with( VENT   , false)
        );
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add( WINDOW );
        builder.add( VENT   );
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if (world.getBlockEntity(pos) instanceof DragonForgeBricksBlockEntity entity) {
            ItemStack stack = new ItemStack(this);
            NbtHelper.setItemDragonType(stack, entity.type);
            return stack;
        } else {
            return super.getPickStack(world, pos, state);
        }
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonForgeBricksBlockEntity(pos, state);
    }


}
