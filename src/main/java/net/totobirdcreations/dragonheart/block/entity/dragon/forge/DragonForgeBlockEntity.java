package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;

import javax.annotation.Nullable;


public abstract class DragonForgeBlockEntity extends DragonBlockEntity {


    public DragonForgeBlockEntity(BlockEntityType entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }


    @Nullable
    public abstract Relation<DragonForgeCoreBlockEntity> getCoreRelation();

}
