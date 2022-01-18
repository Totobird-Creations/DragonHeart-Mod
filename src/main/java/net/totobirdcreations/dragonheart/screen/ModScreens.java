package net.totobirdcreations.dragonheart.screen;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;

public class ModScreens {

    public static ScreenHandlerType<DragonforgeCoreBaseScreenHandler> DRAGONFORGE_CORE_BASE = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_base"),
            DragonforgeCoreBaseScreenHandler::new
    );

    public static ScreenHandlerType<DragonforgeCoreFireScreenHandler> DRAGONFORGE_CORE_FIRE = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_fire"),
            DragonforgeCoreFireScreenHandler::new
    );

    public static ScreenHandlerType<DragonforgeCoreIceScreenHandler> DRAGONFORGE_CORE_ICE = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_ice"),
            DragonforgeCoreIceScreenHandler::new
    );

    public static ScreenHandlerType<DragonforgeCoreLightningScreenHandler> DRAGONFORGE_CORE_LIGHTNING = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_lightning"),
            DragonforgeCoreLightningScreenHandler::new
    );

    public static void register() {
        DragonHeart.LOGGER.info("Registering screens.");
        ScreenRegistry.register(DRAGONFORGE_CORE_BASE      , DragonforgeCoreBaseScreen::new);
        ScreenRegistry.register(DRAGONFORGE_CORE_FIRE      , DragonforgeCoreTypeScreen::new);
        ScreenRegistry.register(DRAGONFORGE_CORE_ICE       , DragonforgeCoreTypeScreen::new);
        ScreenRegistry.register(DRAGONFORGE_CORE_LIGHTNING , DragonforgeCoreTypeScreen::new);
    }

}
