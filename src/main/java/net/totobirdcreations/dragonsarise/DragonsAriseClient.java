package net.totobirdcreations.dragonsarise;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.totobirdcreations.dragonsarise.screen.DragonforgeLightningScreen;
import net.totobirdcreations.dragonsarise.screen.ModScreenHandlers;
import net.totobirdcreations.dragonsarise.util.ModRenderHelper;

public class DragonsAriseClient  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModRenderHelper.setRenderLayers();
        ScreenRegistry.register(ModScreenHandlers.DRAGONFORGE_LIGHTNING_SCREEN_HANDLER, DragonforgeLightningScreen::new);
    }

}
