package net.totobirdcreations.dragonsarise.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonsarise.DragonsArise;

public class ModScreenHandlers {

    public static final ScreenHandlerType<DragonforgeLightningScreenHandler> DRAGONFORGE_LIGHTNING_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonsArise.MOD_ID, "dragonforge_lightning"), DragonforgeLightningScreenHandler::new
    );

    public static void register() {
        DragonsArise.LOGGER.info("Registering screen handlers.");
    }


}
