package net.totobirdcreations.dragonheart.item;


import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;

public class FoodItemComponents {


    public static final FoodComponent DRAGONMEAT_FIRE      = new FoodComponent.Builder().hunger(10).saturationModifier(1.0f).meat().build();
    public static final FoodComponent DRAGONMEAT_ICE       = new FoodComponent.Builder().hunger(200).saturationModifier(1.0f).meat().statusEffect(new StatusEffectInstance(ModStatusEffects.FROZEN, 10, 0), 1.0f).build();
    public static final FoodComponent DRAGONMEAT_LIGHTNING = new FoodComponent.Builder().hunger(10).saturationModifier(1.0f).meat().build();


}
