package net.totobirdcreations.dragonheart.mixin;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.totobirdcreations.dragonheart.item.material.ArmourMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;



@Mixin(ArmorItem.class)
public class DragonArmourMixin {


    @Shadow
    @Final
    public static UUID[] MODIFIERS;

    @Shadow @Final @Mutable
    public Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;


    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void constructor(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings, CallbackInfo ci) {
        UUID uuid = MODIFIERS[slot.getEntitySlotId()];
        if (material instanceof ArmourMaterial) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            this.attributeModifiers.forEach(builder::put);
            builder.put(
                    EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                    new EntityAttributeModifier(
                            uuid,
                            "Armor knockback resistance",
                            material.getKnockbackResistance(),
                            EntityAttributeModifier.Operation.ADDITION
                    )
            );
            this.attributeModifiers = builder.build();
        }
    }


}
