package net.totobirdcreations.dragonheart.block.entity.dragon;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.BlockTags;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.dragon.DragonGriefedBlock;


public class DragonGriefedBlockEntity extends DragonBlockEntity {

    public static int         MAX_RESET_TIME = 200;
    public static NbtCompound DEFAULT_STATE  = (NbtCompound)(BlockState.CODEC.encode(
            Blocks.AIR.getDefaultState(),
            NbtOps.INSTANCE,
            NbtOps.INSTANCE.empty()
    ).get().orThrow());

    public int         resetTime  = MAX_RESET_TIME;
    public Identifier  resetId    = new Identifier("minecraft", "air");
    public NbtCompound resetState = DEFAULT_STATE;
    public NbtCompound resetNbt   = new NbtCompound();


    public DragonGriefedBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_GRIEFED, pos, state);
    }


    public static void convert(World world, BlockPos pos, Identifier type) {
        BlockState resetState = world.getBlockState(pos);
        if (! BlockTags.isOf(resetState, BlockTags.BREATH_IMMUNE)) {
            // If is unresettable griefed block or is not griefed block, create new resettable griefed block.
            if (
                    (resetState.isOf(DragonBlocks.DRAGON_GRIEFED) && ! resetState.get(DragonGriefedBlock.CAN_RESET)) ||
                    (! resetState.isOf(DragonBlocks.DRAGON_GRIEFED))
            ) {
                Identifier  resetId     = Registry.BLOCK.getId(resetState.getBlock());
                BlockEntity resetEntity = world.getBlockEntity(pos);
                NbtCompound resetNbt    = new NbtCompound();
                if (resetEntity != null) {
                    resetEntity.writeNbt(resetNbt);
                }
                world.removeBlockEntity(pos);
                world.setBlockState(pos, DragonBlocks.DRAGON_GRIEFED
                        .getDefaultState()
                        .with(DragonGriefedBlock.CAN_RESET, true)
                );
                if (world.getBlockEntity(pos) instanceof DragonGriefedBlockEntity entity) {
                    entity.setDragon(type);
                    entity.resetId = resetId;
                    try {
                        entity.resetState = (NbtCompound)(BlockState.CODEC.encode(resetState, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).get().orThrow());
                    } catch (Exception ignored) {
                        entity.resetState = new NbtCompound();
                    }
                    entity.resetNbt = resetNbt;
                }
                resetState = world.getBlockState(pos);
            }
            // If is resettable dragon griefed block, set grief type and max reset timer.
            if (resetState.get(DragonGriefedBlock.CAN_RESET) && world.getBlockEntity(pos) instanceof DragonGriefedBlockEntity entity) {
                entity.resetTime = MAX_RESET_TIME;
                entity.setDragon(type);
            }
        }
    }

    public void reset() {
        if (this.world != null) {
            NbtCompound resetNbt   = this.resetNbt;
            BlockState  resetState;
            try {
                resetState = BlockState.CODEC.decode(NbtOps.INSTANCE, this.resetState).get().orThrow().getFirst();
            } catch (Exception ignored) {
                resetState = Registry.BLOCK.get(this.resetId).getDefaultState();
            }
            this.world.removeBlockEntity(this.getPos());
            this.world.setBlockState(this.getPos(), resetState);
            BlockEntity resetEntity = this.world.getBlockEntity(this.getPos());
            if (resetEntity != null) {
                resetEntity.readNbt(resetNbt);
            }
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
        if (state.get(DragonGriefedBlock.CAN_RESET)) {
            entity.resetTime -= 1;
            if (entity.resetTime <= 0) {
                entity.reset();
            }
        }
    }

}
