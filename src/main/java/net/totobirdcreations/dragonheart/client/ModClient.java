package net.totobirdcreations.dragonheart.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.totobirdcreations.dragonheart.client.screen.ClientScreens;
import net.totobirdcreations.dragonheart.sync.ClientChannelHandler;
import net.totobirdcreations.dragonheart.sync.ModSync;


@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, minecraft) -> ModSync.onJoinServer(minecraft));
        ClientPlayNetworking.registerGlobalReceiver(ModSync.CHANNEL, ClientChannelHandler.INSTANCE);

        ClientRenderers  .register();
        ClientShaders    .register();
        ClientScreens    .register();
        ClientRegistries .register();
    }

}
