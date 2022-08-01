package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class DragonForgeApertureBlockEntity extends DragonForgeBlockEntity {


    public DragonForgeApertureBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_APERTURE, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonForgeApertureBlockEntity entity) {}

}
