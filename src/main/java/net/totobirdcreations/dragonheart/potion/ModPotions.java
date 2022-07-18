package net.totobirdcreations.dragonheart.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;

public class ModPotions {


    public static Potion DRAGONBLOOD_FIRE = registerPotion("dragonblood_fire",
            // TODO : Create burning effect.
            new StatusEffectInstance(ModStatusEffects.FROZEN, 200, 0)
    );

    public static Potion DRAGONBLOOD_ICE = registerPotion("dragonblood_ice",
            new StatusEffectInstance(ModStatusEffects.FROZEN, 200, 0)
    );

    public static Potion DRAGONBLOOD_LIGHTNING = registerPotion("dragonblood_lightning",
            // TODO : Create charged effect.
            new StatusEffectInstance(ModStatusEffects.FROZEN, 200, 0)
    );


    public static void register() {
        DragonHeart.LOGGER.info("Registering potions.");
    }

    public static Potion registerPotion(String name, StatusEffectInstance statusEffect) {
        return Registry.register(
                Registry.POTION,
                new Identifier(DragonHeart.MOD_ID, name),
                new Potion(statusEffect)
        );
    }

}
