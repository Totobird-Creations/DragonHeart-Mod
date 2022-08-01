package net.totobirdcreations.dragonheart.block.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonForgeApertureBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;

import javax.annotation.Nullable;


public class DragonForgeApertureBlock extends DragonForgeBlock {


    public DragonForgeApertureBlock(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "forge_aperture";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        Item        item  = DragonBlocks.DRAGON_FORGE_APERTURE.item();
        ItemStack   stack = new ItemStack(item);
        NbtCompound nbt;
        nbt = stack.getOrCreateSubNbt("BlockEntityTag");
        nbt.putString("dragon", dragon.toString());
        stacks.add(stack);
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonForgeApertureBlockEntity(pos, state);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DragonBlockEntities.DRAGON_FORGE_APERTURE, DragonForgeApertureBlockEntity::tick);
    }

}
