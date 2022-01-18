package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.totobirdcreations.dragonheart.item.MiscItems;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.screen.ModScreens;

@Environment(EnvType.CLIENT)
public class DragonHeartClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonscale)stack.getItem()).getColor(stack), MiscItems.DRAGONSCALE);

        ModScreens.register();
    }
}
