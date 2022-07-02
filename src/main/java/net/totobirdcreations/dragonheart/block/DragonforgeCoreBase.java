package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreBaseBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;



public class DragonforgeCoreBase extends DragonforgeCore {


    public DragonforgeCoreBase(Settings settings) {
        super(settings);
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DragonforgeCoreBaseBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.DRAGONFORGE_CORE_BASE, DragonforgeCoreBaseBlockEntity::tick);
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
