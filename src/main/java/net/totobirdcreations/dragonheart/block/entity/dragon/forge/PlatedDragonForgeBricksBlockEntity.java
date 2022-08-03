package net.totobirdcreations.dragonheart.block.entity.dragon.forge;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.forge.PlatedDragonForgeBricksBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.gamerule.Gamerules;


public class PlatedDragonForgeBricksBlockEntity extends DragonBlockEntity {

    public PlatedDragonForgeBricksBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.PLATED_DRAGON_FORGE_BRICKS, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, PlatedDragonForgeBricksBlockEntity entity) {
        if (! world.isClient()) {
            boolean unbreakable = world.getGameRules().getBoolean(Gamerules.PLATED_DRAGON_FORGE_BRICKS_UNBREAKABLE);
            if (state.get(PlatedDragonForgeBricksBlock.UNBREAKABLE) != unbreakable) {
                world.setBlockState(pos, state.with(
                        PlatedDragonForgeBricksBlock.UNBREAKABLE, unbreakable
                ));
            }
        }
    }

}
