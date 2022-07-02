package net.totobirdcreations.dragonheart.item.material;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;


public enum ArmourMaterial implements ArmorMaterial {


    PREFORGED             ( 2500  , new int[]{ 2 , 4  , 5  , 2 }, "dragonbone"            , 0.0f , 0.0f  ),
    DRAGONBONE_FIRE       ( 5000  , new int[]{ 3 , 6  , 8  , 3 }, "dragonbone_fire"       , 3.0f , 0.1f  ),
    DRAGONBONE_ICE        ( 5000  , new int[]{ 3 , 6  , 8  , 3 }, "dragonbone_ice"        , 3.0f , 0.1f  ),
    DRAGONBONE_LIGHTNING  ( 5000  , new int[]{ 3 , 6  , 8  , 3 }, "dragonbone_lightning"  , 3.0f , 0.1f  ),
    DRAGONSTEEL_FIRE      ( 10000 , new int[]{ 6 , 12 , 16 , 6 }, "dragonsteel_fire"      , 6.0f , 10.0f ),
    DRAGONSTEEL_ICE       ( 10000 , new int[]{ 6 , 12 , 16 , 6 }, "dragonsteel_ice"       , 6.0f , 10.0f ),
    DRAGONSTEEL_LIGHTNING ( 10000 , new int[]{ 6 , 12 , 16 , 6 }, "dragonsteel_lightning" , 6.0f , 10.0f );


    public final int    durability;
    public final int[]  protection;
    public final String name;
    public final float  toughness;
    public final float  knockbackResistance;


    ArmourMaterial(int durability, int[] protection, String name, float toughness, float knockbackResistance) {
        this.durability          = durability;
        this.protection          = protection;
        this.name                = name;
        this.toughness           = toughness;
        this.knockbackResistance = knockbackResistance;
    }


    @Override
    public int getDurability(EquipmentSlot slot) {
        return durability;
    }


    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return protection[slot.getEntitySlotId()];
    }


    @Override
    public int getEnchantability() {
        return 0;
    }


    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }


    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public float getToughness() {
        return toughness;
    }


    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }


}
