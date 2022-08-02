package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.DragonForgeHatchBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import java.util.ArrayList;


public class DragonForgeHatchBlock extends DragonForgeBlock {

    public DragonForgeHatchBlock(Settings settings) {
        super(settings);
    }


    @Override
    public String getNameId() {
        return "forge_hatch";
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ItemStack   stack = new ItemStack(DragonBlocks.DRAGON_FORGE_HATCH.item());
        NbtCompound nbt;
        nbt = stack.getOrCreateSubNbt("BlockEntityTag");
        nbt.putString("dragon", NbtHelper.EMPTY_TYPE.toString());
        stacks.add(stack);
    }
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier dragon, DragonResourceLoader.DragonResource resource) {
        ItemStack   stack = new ItemStack(DragonBlocks.DRAGON_FORGE_HATCH.item());
        NbtCompound nbt;
        nbt = stack.getOrCreateSubNbt("BlockEntityTag");
        nbt.putString("dragon", dragon.toString());
        stacks.add(stack);
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        } else {
            open(world, pos, player);
            return ActionResult.CONSUME;
        }
    }

    public static void open(World world, BlockPos pos, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof DragonForgeHatchBlockEntity entity) {
            ArrayList<DragonForgeCoreBlockEntity> relations = entity.getRelation(DragonForgeHatchBlockEntity.CORE_SIDE);
            if (relations.size() == 1) {
                DragonForgeCoreBlockEntity relation = relations.get(0);
                DragonForgeCoreBlock.open(
                        world.getBlockState(relation.getPos()),
                        world,
                        relation.getPos(),
                        player
                );
            }
        }
    }


    @Override
    public DragonBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonForgeHatchBlockEntity(pos, state);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, DragonBlockEntities.DRAGON_FORGE_HATCH, DragonForgeHatchBlockEntity::tick);
    }

}
