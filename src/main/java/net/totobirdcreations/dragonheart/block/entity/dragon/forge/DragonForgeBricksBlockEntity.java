package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonForgeBricksBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.particle_effect.ParticleEffects;

import javax.annotation.Nullable;


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
    public static final Relation<DragonForgeCoreBlockEntity> CORE_ALL = Relation.join(
            DragonBlockEntities.DRAGON_FORGE_CORE,
            CORE_SIDE, CORE_DOWN, CORE_ALT
    );


    public DragonForgeBricksBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_BRICKS, pos, state);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        boolean vent    = false;
        boolean window  = false;
        boolean powered = false;

        for (DragonForgeCoreBlockEntity relation : this.getRelation(CORE_SIDE)) {
            vent = true;
            if (world.getBlockState(relation.getPos()).get(Properties.POWERED)) {
                this.setPower(relation.power);
                powered = true;
                break;
            }
        }
        for (DragonForgeCoreBlockEntity relation : this.getRelation(CORE_DOWN)) {
            window = true;
            if (world.getBlockState(relation.getPos()).get(Properties.POWERED)) {
                this.setPower(relation.power);
                powered = true;
                break;
            }
        }
        for (DragonForgeCoreBlockEntity relation : this.getRelation(CORE_ALT)) {
            if (world.getBlockState(relation.getPos()).get(Properties.POWERED)) {
                this.setPower(relation.power);
                powered = true;
                break;
            }
        }

        world.setBlockState(pos, state
                .with(DragonForgeBricksBlock. VENT    , vent    )
                .with(DragonForgeBricksBlock. WINDOW  , window  )
                .with(Properties.             POWERED , powered )
        );


        if (window && powered && world.isClient()) {
            ParticleEffects.createDragonForgeFlame(world, pos, this.power);
        }
    }


    @Nullable
    public Relation<DragonForgeCoreBlockEntity> getCoreRelation() {
        return CORE_ALL;
    }

}
