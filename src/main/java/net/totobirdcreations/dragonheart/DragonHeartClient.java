package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.entity.DragonFireEntityModel;
import net.totobirdcreations.dragonheart.entity.DragonFireEntityRenderer;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.item.MiscItems;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.packet.PacketBufUtil;
import net.totobirdcreations.dragonheart.screen.ModScreens;
import software.bernie.example.registry.EntityRegistry;

import java.util.UUID;


@Environment(EnvType.CLIENT)
public class DragonHeartClient implements ClientModInitializer {


    public static final EntityModelLayer DRAGONBREATH_FIRE = new EntityModelLayer(new Identifier(DragonHeart.MOD_ID, "dragonbreath_fire"), "main");
    public static final EntityModelLayer DRAGONBREATH_ICE  = new EntityModelLayer(new Identifier(DragonHeart.MOD_ID, "dragonbreath_ice"), "main");

    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonscale)stack.getItem()).getColor(stack), MiscItems.DRAGONSCALE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonbucket)stack.getItem()).getColor(stack), MiscItems.DRAGONBUCKET_ICE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonbucket)stack.getItem()).getColor(stack), MiscItems.DRAGONBUCKET_FIRE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonbucket)stack.getItem()).getColor(stack), MiscItems.DRAGONBUCKET_LIGHTNING);

        EntityRendererRegistry.register(
                ModEntities.DRAGONBREATH_FIRE_TYPE,
                (context) -> {return new FlyingItemEntityRenderer(context);}
        );
        EntityRendererRegistry.register(
                ModEntities.DRAGONBREATH_ICE_TYPE,
                (context) -> {return new FlyingItemEntityRenderer(context);}
        );

        EntityRendererRegistry.register(ModEntities.DRAGON_FIRE, DragonFireEntityRenderer::new);

        receiveEntityPacket();

        ModScreens.register();

    }


    public void receiveEntityPacket() {

        ClientSidePacketRegistry.INSTANCE.register(
                ModEntities.SPAWN_PACKET,
                (ctx, byteBuf) -> {
                    EntityType<?> et       = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
                    UUID          uuid     = byteBuf.readUuid();
                    int           entityId = byteBuf.readVarInt();
                    Vec3d         pos      = PacketBufUtil.readVec3d(byteBuf);
                    float         pitch    = PacketBufUtil.readAngle(byteBuf);
                    float         yaw      = PacketBufUtil.readAngle(byteBuf);
                    ctx.getTaskQueue().execute(() -> {
                        if (MinecraftClient.getInstance().world == null) {
                            throw new IllegalStateException("Tried to spawn entity in null world.");
                        }
                        Entity e = et.create(MinecraftClient.getInstance().world);
                        if (e == null) {
                            throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\".");
                        }
                        e.updateTrackedPosition(pos);
                        e.setPos(pos.x, pos.y, pos.z);
                        e.setPitch(pitch);
                        e.setYaw(yaw);
                        e.setId(entityId);
                        e.setUuid(uuid);
                        MinecraftClient.getInstance().world.addEntity(entityId, e);
                    });
                }
        );

    }


}
