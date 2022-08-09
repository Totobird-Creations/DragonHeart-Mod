package net.totobirdcreations.dragonheart.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.totobirdcreations.dragonheart.DragonHeart;


public class EventHandlers {

    public static void register() {
        DragonHeart.LOGGER.debug("Registering event handlers.");
        ResourceEventHandlers.register();
        StructureEventHandlers.register();
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        DragonHeart.LOGGER.debug("Registering client event handlers.");
        ResourceEventHandlers.registerClient();
    }

}
