package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;



public enum SwordMaterial implements ToolMaterial {


    PREFORGED   ( 2500  , 10.0f , 1.6f ),
    DRAGONBONE  ( 5000  , 15.0f , 1.6f ),
    DRAGONSTEEL ( 10000 , 25.0f , 1.6f );


    public final int   itemDurability;
    public final float attackDamage;
    public final float attackSpeed;


    SwordMaterial(int itemDurability, float attackDamage, float attackSpeed) {

        this.itemDurability = itemDurability;
        this.attackDamage   = attackDamage;
        this.attackSpeed    = attackSpeed;

    }


    @Override
    public int getDurability() {

        return this.itemDurability;

    }


    @Override
    public float getMiningSpeedMultiplier() {

        return 1.0f;

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

        return -1;

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


