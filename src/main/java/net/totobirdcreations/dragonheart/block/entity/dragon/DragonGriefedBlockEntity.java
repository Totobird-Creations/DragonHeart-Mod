package net.totobirdcreations.dragonheart.block.entity.dragon;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;


public class DragonGriefedBlockEntity extends DragonBlockEntity {

    public int         resetTime  = 200;
    public Identifier  resetId;
    public NbtCompound resetState;
    public NbtCompound resetNbt;


    public DragonGriefedBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_GRIEFED, pos, state);
    }


    public static void convert(World world, BlockPos pos, Identifier type) {
        BlockState  resetState  = world.getBlockState(pos);
        Block       resetBlock  = resetState.getBlock();
        Identifier  resetId     = Registry.BLOCK.getId(resetBlock);
        BlockEntity resetEntity = world.getBlockEntity(pos);
        NbtCompound resetNbt    = new NbtCompound();
        if (resetEntity != null) {
            resetEntity.writeNbt(resetNbt);
        }
        world.removeBlockEntity(pos);
        world.setBlockState(pos, DragonBlocks.DRAGON_GRIEFED.block().getDefaultState());
        if (world.getBlockEntity(pos) instanceof DragonGriefedBlockEntity entity) {
            entity.resetId = resetId;
            try {
                entity.resetState = (NbtCompound) (BlockState.CODEC.encode(resetState, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).get().orThrow());
            } catch (Exception ignored) {
                entity.resetState = new NbtCompound();
            }
            entity.resetNbt = resetNbt;
            entity.setDragon(type);
        }
    }

    public void reset(World world, BlockPos pos) {
        NbtCompound resetNbt   = this.resetNbt;

        world.removeBlockEntity(pos);
        DragonHeart.LOGGER.info(this.resetState);
        try {
            world.setBlockState(pos, BlockState.CODEC.decode(NbtOps.INSTANCE, this.resetState).get().orThrow().getFirst());
        } catch (Exception ignored) {
            world.setBlockState(pos, Registry.BLOCK.get(this.resetId).getDefaultState());
        }
        BlockEntity resetEntity = world.getBlockEntity(pos);
        if (resetEntity != null) {
            resetEntity.readNbt(resetNbt);
        }
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        resetTime  = nbt.getInt("resetTime");
        resetId    = new Identifier(nbt.getString("resetId"));
        resetState = nbt.getCompound("resetState");
        resetNbt   = nbt.getCompound("resetNbt");
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("resetTime", resetTime);
        nbt.putString("resetId", resetId.toString());
        nbt.put("resetState", resetState);
        nbt.put("resetNbt", resetNbt);
        super.writeNbt(nbt);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonGriefedBlockEntity entity) {
        entity.resetTime -= 1;
        if (entity.resetTime <= 0) {
            entity.reset(world, pos);
        }
    }

}
