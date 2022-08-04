package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import java.util.ArrayList;


public abstract class DragonBlockEntity extends BlockEntity {

    public Identifier dragon = NbtHelper.EMPTY_TYPE;


    public DragonBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public void setDragon(Identifier id) {
        this.dragon = id;
        this.sync();
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        this.setDragon(new Identifier(nbt.getString("dragon")));
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putString("dragon", this.dragon.toString());
        super.readNbt(nbt);
    }


    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        this.writeNbt(nbt);
        return nbt;
    }


    public void sync() {
        if (this.world != null) {
            this.markDirty();
            this.world.updateListeners(this.getPos(), this.getCachedState(), this.world.getBlockState(this.getPos()), Block.NOTIFY_ALL);
        }
    }



    public <T extends DragonBlockEntity> ArrayList<T> getRelation(Relation<T> relation) {
        ArrayList<T> result = new ArrayList<>();
        for (Vec3i offset : relation.offsets) {
            if (this.world != null) {
                BlockEntity blockEntity = this.world.getBlockEntity(this.getPos().add(offset));
                if (blockEntity != null && blockEntity.getType() == relation.blockEntity) {
                    T dragonBlockEntity = (T) blockEntity;
                    if (dragonBlockEntity.dragon.equals(this.dragon)) {
                        result.add(dragonBlockEntity);
                    }
                }
            }
        }
        return result;
    }

    public record Relation<T extends DragonBlockEntity>(
            BlockEntityType<T> blockEntity,
            Vec3i... offsets
    ) {}

}
