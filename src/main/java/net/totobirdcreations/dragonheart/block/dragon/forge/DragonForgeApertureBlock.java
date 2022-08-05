package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.DragonForgeApertureBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class DragonForgeApertureBlock extends DragonForgeBlock {


    public DragonForgeApertureBlock(AbstractBlock.Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "forge_aperture";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_FORGE_APERTURE.item());
        NbtHelper.setItemDragonType(stack, NbtHelper.EMPTY_TYPE);
        stacks.add(stack);
    }
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        ItemStack stack = new ItemStack(DragonBlocks.DRAGON_FORGE_APERTURE.item());
        NbtHelper.setItemDragonType(stack, dragon);
        stacks.add(stack);
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        if (world.getBlockEntity(pos) instanceof DragonForgeApertureBlockEntity entity) {
            ItemStack stack = new ItemStack(this);
            NbtHelper.setItemDragonType(stack, entity.dragon);
            return stack;
        } else {
            return super.getPickStack(world, pos, state);
        }
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonForgeApertureBlockEntity(pos, state);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BlockWithEntity.checkType(type, DragonBlockEntities.DRAGON_FORGE_APERTURE, DragonForgeApertureBlockEntity::tick);
    }

}
