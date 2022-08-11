package net.totobirdcreations.dragonheart.block.entity.dragon;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.dragon.DragonGriefedBlock;
import net.totobirdcreations.dragonheart.resource.TagResources;


public class DragonGriefedBlockEntity extends DragonBlockEntity {

    public static int MAX_RESET_TIME = 200;

    public int         resetTime  = MAX_RESET_TIME;
    public Identifier  resetId    = new Identifier("minecraft", "air");
    public BlockState  resetState = Blocks.AIR.getDefaultState();
    public NbtCompound resetNbt   = new NbtCompound();


    public DragonGriefedBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_GRIEFED, pos, state);
    }


    public static void convert(World world, BlockPos pos, Identifier type) {
        BlockState resetState = world.getBlockState(pos);
        if (! TagResources.isOf(resetState, TagResources.BREATH_IMMUNE)) {
            // If is unresettable griefed block or is not griefed block, create new resettable griefed block.
            if ((resetState.isOf(DragonBlocks.DRAGON_GRIEFED)
                    && ! resetState.get(DragonGriefedBlock.CAN_RESET)
            ) || (! resetState.isOf(DragonBlocks.DRAGON_GRIEFED))) {
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
                    entity.resetId    = resetId;
                    entity.resetState = resetState;
                    entity.resetNbt   = resetNbt;
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
            NbtCompound resetNbt = this.resetNbt;
            this.world.removeBlockEntity(this.getPos());
            this.world.setBlockState(this.getPos(), this.resetState);
            BlockEntity resetEntity = this.world.getBlockEntity(this.getPos());
            if (resetEntity != null) {
                resetEntity.readNbt(resetNbt);
            }
        }
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        this.resetTime = nbt.getInt("resetTime");
        this.resetId   = new Identifier(nbt.getString("resetId"));
        try {
            this.resetState = BlockState.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("resetState")).get().orThrow().getFirst();
        } catch (Exception ignored) {
            this.resetState = Registry.BLOCK.get(this.resetId).getDefaultState();
        }
        this.resetNbt = nbt.getCompound("resetNbt");
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt    ( "resetTime"  , this.resetTime          );
        nbt.putString ( "resetId"    , this.resetId.toString() );
        NbtCompound resetState = new NbtCompound();
        try {
            resetState = (NbtCompound)(BlockState.CODEC.encode(this.resetState, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).get().orThrow());
        } catch (Exception ignored) {}
        nbt.put( "resetState" , resetState    );
        nbt.put( "resetNbt"   , this.resetNbt );
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
