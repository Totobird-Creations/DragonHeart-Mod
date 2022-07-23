package net.totobirdcreations.dragonheart.sync;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;


public class ModSync {

    public static Identifier CHANNEL = new Identifier(DragonHeart.MOD_ID, "sync");


    public static void register() {
        //ServerPlayConnectionEvents.JOIN.register((handler, sender, minecraft) -> ModSync.onClientJoined(handler, minecraft));
        ServerPlayNetworking.registerGlobalReceiver(CHANNEL, ServerChannelHandler.INSTANCE);
    }


    // Client calls when joined server.
    @Environment(EnvType.CLIENT)
    public static void onJoinServer(MinecraftClient minecraft) {
        boolean singleplayer = minecraft.isInSingleplayer();
        if (! singleplayer) {
            if (ClientPlayNetworking.canSend(CHANNEL)) {
                onJoinServerFailure(minecraft);
            }
        }

    }


    @Environment(EnvType.CLIENT)
    public static void onJoinServerFailure(MinecraftClient minecraft) {
        if (minecraft.world != null) {
            minecraft.world.disconnect();
        }
        minecraft.disconnect(new DisconnectedScreen(
                new TitleScreen(),
                Text.translatable("multiplayer.disconnect.generic"),
                Text.translatable("sync.dragonheart.connect.server_missing")
        ));
    }


    @Environment(EnvType.CLIENT)
    public static Text getMetadataDescription_serverMissing() {
        return Text.translatable("sync.dragonheart.list.server_missing");
    }


    @Environment(EnvType.SERVER)
    public static void onClientJoinedFailure(ClientConnection connection) {
        //Text reason = Text.literal(Text.translatable("sync.dragonheart.fail.client_missing").getString());
        Text reason = Text.literal("Failed to join the server due to you not having the DragonHeart mod installed. Please install the mod before joining this server.");
        connection.send(new DisconnectS2CPacket(reason));
        connection.disconnect(reason);
    }


    @Environment(EnvType.SERVER)
    public static Text getMetadataDescription_clientMissing() {
        return Text.literal("You do not have the DragonHeart mod installed.")
                .formatted(Formatting.DARK_RED);
    }

}
