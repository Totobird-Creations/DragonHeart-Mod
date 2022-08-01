package net.totobirdcreations.dragonheart.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;


public class SoundEvents {


    public static final SoundEvent DRAGONEGG_PLACE = registerSoundEvent("dragonegg.place");
    public static final SoundEvent DRAGONEGG_BREAK = registerSoundEvent("dragonegg.break");
    public static final SoundEvent DRAGONEGG_HATCH = registerSoundEvent("dragonegg.hatch");


    public static SoundEvent registerSoundEvent(String name) {
        Identifier identifier = new Identifier(DragonHeart.ID, name);
        return Registry.register(
                Registry.SOUND_EVENT,
                identifier,
                new SoundEvent(identifier)
        );
    }

    public static void register() {
        DragonHeart.LOGGER.info("Registering sound events.");
    }

}
