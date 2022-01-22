package net.totobirdcreations.dragonheart.item.material;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;



public enum AxeMaterial implements ToolMaterial {


    PREFORGED   ( 2500  , 5.0f  , 3.0f  , 1.0f ),
    DRAGONBONE  ( 5000  , 15.0f , 10.0f , 1.0f ),
    DRAGONSTEEL ( 10000 , 25.0f , 15.0f , 1.0f );


    public final int itemDurability;
    public final float miningSpeed;
    public final float attackDamage;
    public final float attackSpeed;


    AxeMaterial(int itemDurability, float miningSpeed, float attackDamage, float attackSpeed) {

        this.itemDurability = itemDurability;
        this.miningSpeed    = miningSpeed;
        this.attackDamage   = attackDamage;
        this.attackSpeed    = attackSpeed;

    }


    @Override
    public int getDurability() {

        return this.itemDurability;

    }


    @Override
    public float getMiningSpeedMultiplier() {

        return this.miningSpeed;

    }


    @Override
    public float getAttackDamage() {

        return this.attackDamage - 1.0f;

    }


    public float getAttackSpeed() {

        return this.attackSpeed - 4.0f;

    }


    @Override
    public int getMiningLevel() {

        return 5;

    }


    @Override
    public int getEnchantability() {

        return 0;

    }


    @Override
    public Ingredient getRepairIngredient() {

        return null;

    }


}
