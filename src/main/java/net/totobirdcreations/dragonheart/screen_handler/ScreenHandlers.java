package net.totobirdcreations.dragonheart.screen_handler;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;


public class ScreenHandlers {


    @SuppressWarnings("deprecation")
    public static ScreenHandlerType<DragonEggIncubatorScreenHandler> DRAGONEGG_INCUBATOR = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonHeart.ID, "dragon_egg_incubator"),
            DragonEggIncubatorScreenHandler::new
    );


    @SuppressWarnings("deprecation")
    public static ScreenHandlerType<DragonForgeCoreScreenHandler> DRAGON_FORGE_CORE = ScreenHandlerRegistry.registerSimple(
            new Identifier(DragonHeart.ID, "dragon_forge_core"),
            DragonForgeCoreScreenHandler::new
    );


    public static void register() {
        DragonHeart.LOGGER.debug("Registering screen handlers.");
    }

}
