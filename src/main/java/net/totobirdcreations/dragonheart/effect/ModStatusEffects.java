package net.totobirdcreations.dragonheart.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;



public class ModStatusEffects {


    public static final StatusEffect FROZEN = registerStatusEffect(
            "frozen", new FrozenStatusEffect()
    );

    public static final StatusEffect CHARGED = registerStatusEffect(
            "charged", new ChargedStatusEffect()
    );


    public static StatusEffect registerStatusEffect(String name, StatusEffect object) {

        return Registry.register(Registry.STATUS_EFFECT, new Identifier(DragonHeart.MOD_ID, name), object);

    }


    public static void register() {

        DragonHeart.LOGGER.info("Registering status effects.");

    }


}
