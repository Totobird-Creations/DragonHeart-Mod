package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;


public abstract class DragonForgeBlockEntity extends DragonBlockEntity {


    public DragonForgeBlockEntity(BlockEntityType entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

}
