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
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.DragonForgeBlockEntity;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.*;


public abstract class DragonBlockEntity extends BlockEntity {

    public Identifier type  = NbtHelper.EMPTY_TYPE;
    public Identifier power = NbtHelper.EMPTY_TYPE;


    public DragonBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public void setType(Identifier id) {
        this.type = id;
        this.sync();
    }
    public void setPower(Identifier id) {
        this.power = id;
        this.sync();
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        this.type  = new Identifier(nbt.getString( "type"  ));
        this.power = new Identifier(nbt.getString( "power" ));
        this.sync();
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putString( "type"  , this.type.toString()  );
        nbt.putString( "power" , this.power.toString() );
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
            this.world.updateListeners(this.getPos(), this.getCachedState(), this.world.getBlockState(this.getPos()), Block.NOTIFY_ALL);
        }
        this.markDirty();
    }



    public <T extends DragonForgeBlockEntity> Collection<T> getRelation(@Nullable Relation<T> relation) {
        return this.getRelation(relation, false, false);
    }
    public <T extends DragonForgeBlockEntity> Collection<T> getRelation(@Nullable Relation<T> relation, boolean sameType, boolean untypedStrict) {
        Collection<T> result = new ArrayList<>();
        if (relation != null) {
            for (Vec3i offset : relation.offsets) {
                if (this.world != null) {
                    BlockEntity entity = this.world.getBlockEntity(this.getPos().add(offset));
                    if (entity != null
                            && (relation.blockEntity == null
                            ||  entity.getType()     == relation.blockEntity
                    )) {
                        if (entity instanceof DragonBlockEntity dragonEntity) {
                            if ((! sameType ||
                                    this.type.equals(dragonEntity.type)
                            ) || (untypedStrict
                                    && this.type.equals(NbtHelper.EMPTY_TYPE)
                                    && this.power.equals(dragonEntity.type)
                            )) {
                                result.add((T) dragonEntity);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }


    public record Relation<T extends DragonForgeBlockEntity>(
            @Nullable BlockEntityType<T> blockEntity,
            ArrayList<Vec3i> offsets
    ) {

        public Relation(@Nullable BlockEntityType<T> blockEntity, Vec3i... offsets) {
            this(blockEntity, new ArrayList<>(List.of(offsets)));
        }

        public Relation<T> addOffsetsFrom(Relation<?> relation) {
            this.offsets.addAll(relation.offsets);
            return this;
        }

        public static <T extends DragonForgeBlockEntity> Relation<T> empty(@Nullable BlockEntityType<T> blockEntity) {
            return new Relation<>(blockEntity);
        }

        @SafeVarargs
        public static <T extends DragonForgeBlockEntity> Relation<T> join(@Nullable BlockEntityType<T> blockEntity, Relation<T>... relations) {
            return join(blockEntity, List.of(relations));
        }
        public static <T extends DragonForgeBlockEntity> Relation<T> join(@Nullable BlockEntityType<T> blockEntity, Collection<Relation<T>> relations) {
            Set<Vec3i> offsets = new HashSet<>();
            for (Relation<T> relation : relations) {
                offsets.addAll(relation.offsets);
            }
            return new Relation<>(
                    blockEntity,
                    new ArrayList<>(offsets)
            );
        }

    }


    public abstract void tick(World world, BlockPos pos, BlockState state);

}
