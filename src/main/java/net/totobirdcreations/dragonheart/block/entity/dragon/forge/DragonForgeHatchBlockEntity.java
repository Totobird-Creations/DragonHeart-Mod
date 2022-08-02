package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonForgeBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;


public class DragonForgeHatchBlockEntity extends DragonForgeBlockEntity {

    public static final Relation<DragonForgeCoreBlockEntity> CORE_SIDE = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)
    );


    public DragonForgeHatchBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_HATCH, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonForgeHatchBlockEntity entity) {
        boolean powered = false;

        for (DragonForgeCoreBlockEntity relation : entity.getRelation(CORE_SIDE)) {
            if (world.getBlockState(relation.getPos()).get(DragonForgeBlock.POWERED)) {
                powered = true;
                break;
            }
        }

        world.setBlockState(pos, state
                .with(DragonForgeBlock.POWERED, powered)
        );
    }

}
