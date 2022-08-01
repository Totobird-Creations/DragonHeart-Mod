package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.totobirdcreations.dragonheart.util.helper.InventoryHelper;

public abstract class DragonForgeInventoryBlockEntity extends DragonForgeBlockEntity implements NamedScreenHandlerFactory, InventoryHelper {

    public DragonForgeInventoryBlockEntity(BlockEntityType entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

}
