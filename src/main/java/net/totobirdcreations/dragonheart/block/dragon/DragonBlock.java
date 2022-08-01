package net.totobirdcreations.dragonheart.block.dragon;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class DragonBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final BooleanProperty POWERED = BooleanProperty.of("powered");


    public DragonBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(POWERED, false)
        );
    }


    public abstract String getNameId();

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED);
    }


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        Set<Identifier> identifiers = DragonResourceLoader.getIdentifiers();
        for (Identifier identifier : identifiers) {
            this.appendStacks(stacks, identifier, DragonResourceLoader.getResource(identifier));
        }
    }
    public abstract void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource);

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltips, TooltipContext context) {
        super.appendTooltip(stack, world, tooltips, context);
        if (context.isAdvanced()) {
            tooltips.add(Text.translatable("text.debug." + DragonHeart.ID + ".dragon.type",
                    NbtHelper.getItemStackDragonType(stack)
            ).formatted(Formatting.GRAY));
        }
    }


    @Nullable
    @Override
    public abstract DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state);


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @SuppressWarnings("deprecation")
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }


    public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type);

}
