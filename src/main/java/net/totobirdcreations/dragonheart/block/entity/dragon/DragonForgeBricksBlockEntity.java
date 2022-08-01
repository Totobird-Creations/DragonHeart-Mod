package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonForgeBlock;
import net.totobirdcreations.dragonheart.block.dragon.DragonForgeBricksBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntity;


public class DragonForgeBricksBlockEntity extends DragonForgeBlockEntity {

    public static final DragonBlockEntity.Relation<DragonForgeCoreBlockEntity> CORE_SIDE = new DragonBlockEntity.Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)
    );
    public static final DragonBlockEntity.Relation<DragonForgeCoreBlockEntity> CORE_DOWN = new DragonBlockEntity.Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(0, -1, 0)
    );


    public DragonForgeBricksBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_BRICKS, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonForgeBricksBlockEntity entity) {
        boolean vent    = false;
        boolean window  = false;
        boolean powered = false;

        for (DragonForgeCoreBlockEntity relation : entity.getRelation(CORE_SIDE)) {
            vent = true;
            if (world.getBlockState(relation.getPos()).get(DragonForgeBlock.POWERED)) {
                powered = true;
                break;
            }
        }
        for (DragonForgeCoreBlockEntity relation : entity.getRelation(CORE_DOWN)) {
            window = true;
            if (world.getBlockState(relation.getPos()).get(DragonForgeBlock.POWERED)) {
                powered = true;
                break;
            }
        }

        world.setBlockState(pos, state
                .with(DragonForgeBricksBlock. VENT    , vent    )
                .with(DragonForgeBricksBlock. WINDOW  , window  )
                .with(DragonForgeBlock.       POWERED , powered )
        );
    }

}
