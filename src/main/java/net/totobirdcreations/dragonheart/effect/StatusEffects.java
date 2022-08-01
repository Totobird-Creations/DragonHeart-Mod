package net.totobirdcreations.dragonheart.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;



public class StatusEffects {


    public static final StatusEffect FROZEN = registerStatusEffect(
            "frozen", new FrozenStatusEffect()
                    .addAttributeModifier( EntityAttributes.GENERIC_MOVEMENT_SPEED , "7107DE5E-7CE8-4030-940E-514C1F160890" , -1.0f  , EntityAttributeModifier.Operation.MULTIPLY_TOTAL )
                    .addAttributeModifier( EntityAttributes.GENERIC_ATTACK_SPEED   , "55FCED67-E92A-486E-9800-B47F202C4386" , -0.75f , EntityAttributeModifier.Operation.MULTIPLY_TOTAL )
                    .addAttributeModifier( EntityAttributes.GENERIC_ATTACK_DAMAGE  , "22653B89-116E-49DC-9B6B-9971489B5BE5" , -1.0f  , EntityAttributeModifier.Operation.MULTIPLY_TOTAL )
    );
    public static final StatusEffect DEAFENED = registerStatusEffect(
            "deafened", new DeafenedStatusEffect()
    );


    public static StatusEffect registerStatusEffect(String name, StatusEffect object) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(DragonHeart.ID, name), object);
    }


    public static void register() {
        DragonHeart.LOGGER.info("Registering status effects.");
    }


}
