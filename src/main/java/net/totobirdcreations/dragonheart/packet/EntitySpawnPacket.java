package net.totobirdcreations.dragonheart.packet;


import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntitySpawnPacket {


    public static Packet<?> create(Entity e, Identifier packetId) {
        if (e.world.isClient()) {
            throw new IllegalStateException("SpawnPacketUtil.create called on the logical client.");
        }
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
        byteBuf.writeUuid(e.getUuid());
        byteBuf.writeVarInt(e.getId());

        PacketBufUtil.writeVec3d(byteBuf, e.getPos());
        PacketBufUtil.writeAngle(byteBuf, e.getPitch());
        PacketBufUtil.writeAngle(byteBuf, e.getYaw());
        return ServerPlayNetworking.createS2CPacket(packetId, byteBuf);
    }


}
