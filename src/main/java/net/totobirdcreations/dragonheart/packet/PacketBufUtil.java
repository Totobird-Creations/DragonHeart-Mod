package net.totobirdcreations.dragonheart.packet;


import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PacketBufUtil {


    public static byte packAngle(float angle) {

        return (byte)MathHelper.floor(angle * 256 / 360);

    }


    public static float unpackAngle(byte angleByte) {

        return (angleByte * 360) / 256f;

    }


    public static void writeAngle(PacketByteBuf byteBuf, float angle) {

        byteBuf.writeByte(packAngle(angle));

    }


    public static float readAngle(PacketByteBuf byteBuf) {
        return unpackAngle(byteBuf.readByte());
    }


    public static void writeVec3d(PacketByteBuf byteBuf, Vec3d vec3d) {

        byteBuf.writeDouble(vec3d.x);
        byteBuf.writeDouble(vec3d.y);
        byteBuf.writeDouble(vec3d.z);

    }


    public static Vec3d readVec3d(PacketByteBuf byteBuf) {

        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        return new Vec3d(x, y, z);

    }


}
