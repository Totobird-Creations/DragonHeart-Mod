package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonForgeBlock;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonForgeBricksBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.particle.Particles;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;


public class DragonForgeBricksBlockEntity extends DragonForgeBlockEntity {

    public static final Relation<DragonForgeCoreBlockEntity> CORE_SIDE = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)
    );
    public static final Relation<DragonForgeCoreBlockEntity> CORE_DOWN = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(0, -1, 0)
    );
    public static final Relation<DragonForgeCoreBlockEntity> CORE_ALT = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            new BlockPos(-1, -1, 0),
            new BlockPos(-1, 1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(1, 1, 0),
            new BlockPos(-1, 0, -1),
            new BlockPos(-1, 0, 1),
            new BlockPos(1, 0, -1),
            new BlockPos(1, 0, 1),
            new BlockPos(0, -1, -1),
            new BlockPos(0, -1, 1),
            new BlockPos(0, 1, -1),
            new BlockPos(0, 1, 1)
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
        for (DragonForgeCoreBlockEntity relation : entity.getRelation(CORE_ALT)) {
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


        if (window && powered && world.isClient()) {
            RGBColour colour = DragonResourceLoader.getResource(entity.dragon).colourGlow();
            world.addParticle(
                    Particles.DRAGON_FORGE_ACTIVE,
                    pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5,
                    colour.r, colour.g, colour.b
            );
        }
    }

}
