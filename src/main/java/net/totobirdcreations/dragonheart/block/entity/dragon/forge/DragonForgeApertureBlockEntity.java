package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;

import javax.annotation.Nullable;


public class DragonForgeApertureBlockEntity extends DragonForgeBlockEntity {

    public static final Relation<DragonForgeCoreBlockEntity> CORE_SIDE = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)
    );


    public DragonForgeApertureBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_APERTURE, pos, state);
    }


    public void tick(World world, BlockPos pos, BlockState state) {}


    @Nullable
    public Relation<DragonForgeCoreBlockEntity> getCoreRelation() {
        return CORE_SIDE;
    }

}
