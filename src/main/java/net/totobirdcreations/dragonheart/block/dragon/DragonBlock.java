package net.totobirdcreations.dragonheart.block.dragon;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;


public abstract class DragonBlock extends BlockWithEntity implements BlockEntityProvider {


    public DragonBlock(Settings settings) {
        super(settings);
    }


    // Used for getting the item/block name in the translation file.
    public String getNameId() {
        return Registry.BLOCK.getId(this).getPath();
    };


    // Add items to creative inventory.
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        Set<Identifier> identifiers = DragonResourceLoader.getIdentifiers();
        this.appendStacks(stacks);
        for (Identifier identifier : identifiers) {
            this.appendStacks(stacks, identifier, DragonResourceLoader.getResource(identifier));
        }
    }
    // Add base item to creative inventory.
    public void appendStacks(DefaultedList<ItemStack> stacks) {}
    // Add typed item to creative inventory.
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {}

    // Add debug information to tooltip when necessary.
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltips, TooltipContext context) {
        super.appendTooltip(stack, world, tooltips, context);
        if (context.isAdvanced()) {
            tooltips.add(Text.translatable("text.debug." + DragonHeart.ID + ".dragon.type",
                    NbtHelper.getItemDragonType(stack)
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


    // Block can not be pushed.
    @SuppressWarnings("deprecation")
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }


    public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type);


    // If powered, glow.
    public static int getLuminance(BlockState state) {
        if (state.getBlock() instanceof DragonBlock block) {
            return block.getLightLevel(state);
        }
        return 0;
    }
    public int getLightLevel(BlockState state) {
        return 0;
    }

}
