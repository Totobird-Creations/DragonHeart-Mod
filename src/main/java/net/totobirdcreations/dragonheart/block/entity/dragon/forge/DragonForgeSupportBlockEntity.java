package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;


public class DragonForgeSupportBlockEntity extends DragonForgeBlockEntity {

    public static final Relation<DragonForgeCoreBlockEntity> CORE_CORNER = new Relation<>(
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
            if (world.getBlockState(relation.getPos()).get(Properties.POWERED)) {
                powered = true;
                break;
            }
        }

        world.setBlockState(pos, state
                .with(Properties.POWERED, powered)
        );
    }

}
