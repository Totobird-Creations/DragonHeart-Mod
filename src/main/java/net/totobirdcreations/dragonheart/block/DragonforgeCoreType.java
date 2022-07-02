package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreFireBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreIceBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreLightningBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;



public class DragonforgeCoreType extends DragonforgeCore {


    public DragonforgeCoreType(Settings settings) {
        super(settings);
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_FIRE.block)) {
            return new DragonforgeCoreFireBlockEntity(pos, state);
        } else if (state.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_ICE.block)) {
            return new DragonforgeCoreIceBlockEntity(pos, state);
        } else if (state.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_LIGHTNING.block)) {
            return new DragonforgeCoreLightningBlockEntity(pos, state);
        } else {
            return null;
        }
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (state.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_FIRE.block)) {
            return checkType(type, ModBlockEntities.DRAGONFORGE_CORE_FIRE, DragonforgeCoreFireBlockEntity::tick);
        } else if (state.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_ICE.block)) {
            return checkType(type, ModBlockEntities.DRAGONFORGE_CORE_ICE, DragonforgeCoreIceBlockEntity::tick);
        } else if (state.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_LIGHTNING.block)) {
            return checkType(type, ModBlockEntities.DRAGONFORGE_CORE_LIGHTNING, DragonforgeCoreLightningBlockEntity::tick);
        } else {
            return null;
        }
    }


    @Override
    public void openScreen(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (! world.isClient()) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
    }


}
