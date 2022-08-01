package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonForgeBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntity;


public class DragonForgeSupportBlockEntity extends DragonForgeBlockEntity {

    public static final DragonBlockEntity.Relation<DragonForgeCoreBlockEntity> CORE_CORNER = new DragonBlockEntity.Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(-1, -1, -1),
            new BlockPos(-1, -1, 1),
            new BlockPos(-1, 1, -1),
            new BlockPos(-1, 1, 1),
            new BlockPos(1, -1, -1),
            new BlockPos(1, -1, 1),
            new BlockPos(1, 1, -1),
            new BlockPos(1, 1, 1)
    );


    public DragonForgeSupportBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_SUPPORT, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonForgeSupportBlockEntity entity) {
        boolean powered = false;

        for (DragonForgeCoreBlockEntity relation : entity.getRelation(CORE_CORNER)) {
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
