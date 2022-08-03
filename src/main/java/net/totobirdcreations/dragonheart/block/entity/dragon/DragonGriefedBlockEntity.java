package net.totobirdcreations.dragonheart.block.entity.dragon;


import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class DragonGriefedBlockEntity extends DragonBlockEntity {

    public DragonGriefedBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_GRIEFED, pos, state);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonGriefedBlockEntity entity) {}

}
