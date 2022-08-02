package net.totobirdcreations.dragonheart.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.client.screen.ClientScreens;
import net.totobirdcreations.dragonheart.event.EventHandlers;


@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientRenderers  .register();
        ClientShaders    .register();
        ClientScreens    .register();
        ClientRegistries .register();
        ClientParticles  .register();
        EventHandlers    .registerClient();

    }

}
