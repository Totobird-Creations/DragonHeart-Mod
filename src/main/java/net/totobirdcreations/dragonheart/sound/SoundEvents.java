package net.totobirdcreations.dragonheart.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;


public class SoundEvents {


    public static final SoundEvent DRAGON_EGG_PLACE = registerSoundEvent("dragon_egg.place");
    public static final SoundEvent DRAGON_EGG_BREAK = registerSoundEvent("dragon_egg.break");
    public static final SoundEvent DRAGON_EGG_HATCH = registerSoundEvent("dragon_egg.hatch");


    public static SoundEvent registerSoundEvent(String name) {
        Identifier identifier = new Identifier(DragonHeart.ID, name);
        return Registry.register(
                Registry.SOUND_EVENT,
                identifier,
                new SoundEvent(identifier)
        );
    }

    public static void register() {
        DragonHeart.LOGGER.debug("Registering sound events.");
    }

}
