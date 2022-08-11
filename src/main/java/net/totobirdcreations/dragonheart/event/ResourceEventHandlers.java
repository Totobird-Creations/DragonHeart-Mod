package net.totobirdcreations.dragonheart.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;


public class ResourceEventHandlers {


    public static Collection<ServerPlayNetworkHandler> handlers = new ArrayList<>();


    public static void serverside_add_player(ServerPlayNetworkHandler handler) {
        handlers.add(handler);
        serverside_send_dragonresource_signal(handler);
    }
    public static void serverside_remove_player(ServerPlayNetworkHandler handler) {
        handlers.remove(handler);
    }


    public static void serverside_send_dragonresource_signal() {
        // Send update to clients.
        for (ServerPlayNetworkHandler handler : handlers) {
            serverside_send_dragonresource_signal(handler);
        }
    }
    public static void serverside_send_dragonresource_signal(ServerPlayNetworkHandler handler) {
        PacketByteBuf buf    = PacketByteBufs.create();
        Packet<?>     packet = ServerPlayNetworking.createS2CPacket(DragonResourceLoader.RESET_CHANNEL, buf);
        handler.sendPacket(packet);
        for (Identifier path : DragonResourceLoader.getIdentifiers()) {
            DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(path);
            buf = PacketByteBufs.create();
            String data = resource.getString(path);
            buf.writeBytes(data.getBytes(StandardCharsets.UTF_8));
            packet = ServerPlayNetworking.createS2CPacket(DragonResourceLoader.REGISTER_CHANNEL, buf);
            handler.sendPacket(packet);
        }
    }


    @Environment(EnvType.CLIENT)
    public static void clientside_receive_dragonresource_reset_signal() {
        DragonResourceLoader.INSTANCE.dragonResources.clear();
    }
    @Environment(EnvType.CLIENT)
    public static void clientside_receive_dragonresource_register_signal(PacketByteBuf buf) {
        String data = buf.readBytes(buf.readableBytes())
                .toString(StandardCharsets.UTF_8);
        Collection<String> parts = DragonResourceLoader.DragonResource.getParts(data);
        if (parts.size() == 8) {
            DragonResourceLoader.DragonResource resource = DragonResourceLoader.DragonResource.fromParts(parts);
            if (resource != null) {
                DragonResourceLoader.INSTANCE.dragonResources.put(
                        new Identifier(parts.iterator().next()),
                        resource
                );
            }
        }
    }


    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (! (server instanceof IntegratedServer && server.isHost(handler.getPlayer().getGameProfile()))) {
                serverside_add_player(handler);
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> serverside_remove_player(handler));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> clientside_receive_dragonresource_reset_signal());
        ClientPlayNetworking.registerGlobalReceiver( DragonResourceLoader.RESET_CHANNEL    , (client, handler, buf, sender) -> clientside_receive_dragonresource_reset_signal()       );
        ClientPlayNetworking.registerGlobalReceiver( DragonResourceLoader.REGISTER_CHANNEL , (client, handler, buf, sender) -> clientside_receive_dragonresource_register_signal(buf) );
    }

}
