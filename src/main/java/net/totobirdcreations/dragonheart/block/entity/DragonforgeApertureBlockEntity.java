package net.totobirdcreations.dragonheart.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.DragonforgeAperture;

import static net.totobirdcreations.dragonheart.block.DragonforgeStructureBlock.updateNearby;


public class DragonforgeApertureBlockEntity extends BlockEntity {


    public int power = 0;


    public DragonforgeApertureBlockEntity(BlockPos pos, BlockState state) {

        super(ModBlockEntities.DRAGONFORGE_APERTURE, pos, state);

    }


    @Override
    public void readNbt(NbtCompound nbt) {

        super.readNbt(nbt);
        power = nbt.getShort("power");
        if (power >= 1) {
            world.setBlockState(pos, world.getBlockState(pos).with(DragonforgeAperture.POWERED, true));
            updateNearby(world, pos, world.getBlockState(pos), ((DragonforgeAperture)world.getBlockState(pos).getBlock()).coreBlocks);
        }

    }


    @Override
    public void writeNbt(NbtCompound nbt) {

        nbt.putShort("power", (short)power);
        super.writeNbt(nbt);

    }


    public void breathedOn() {

        if (power <= 0) {
            world.setBlockState(pos, world.getBlockState(pos).with(DragonforgeAperture.POWERED, true));
            updateNearby(world, pos, world.getBlockState(pos), ((DragonforgeAperture)world.getBlockState(pos).getBlock()).coreBlocks);
        }
        power = 20;

    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonforgeApertureBlockEntity entity) {

        if (entity.power >= 1) {
            entity.power -= 1;
            if (entity.power <= 0) {
                world.setBlockState(pos, world.getBlockState(pos).with(DragonforgeAperture.POWERED, false));
                updateNearby(world, pos, world.getBlockState(pos), ((DragonforgeAperture)world.getBlockState(pos).getBlock()).coreBlocks);
            }
        }

    }


}
