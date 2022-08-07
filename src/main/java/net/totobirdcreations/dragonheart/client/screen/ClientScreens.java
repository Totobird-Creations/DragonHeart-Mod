package net.totobirdcreations.dragonheart.client.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.screen_handler.ScreenHandlers;


public class ClientScreens {

    public static void register() {
        DragonHeart.LOGGER.debug("Registering client screens.");

        HandledScreens.register(ScreenHandlers. DRAGONEGG_INCUBATOR , DragonEggIncubatorScreen::new);
        HandledScreens.register(ScreenHandlers. DRAGON_FORGE_CORE   , DragonForgeCoreScreen    ::new);

    }

}
