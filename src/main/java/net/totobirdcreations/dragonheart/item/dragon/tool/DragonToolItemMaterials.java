package net.totobirdcreations.dragonheart.item.dragon.tool;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;


public enum DragonToolItemMaterials implements ToolMaterial {

    UNINFUSED_DRAGON_BONE (MiningLevels.IRON      , 2031 , 5.0f  , 5.0f  , 0 , null                                           ),
    DRAGON_BONE           (MiningLevels.NETHERITE , 3515 , 17.5f , 10.0f , 0 , null                                           ),
    DRAGON_STEEL          (MiningLevels.NETHERITE , 5000 , 30.0f , 20.0f , 0 , DragonBlocks.PLATED_DRAGON_FORGE_BRICKS.item() );


    public final int        miningLevel;
    public final int        durability;
    public final float      miningSpeed;
    public final float      attackDamage;
    public final int        enchantability;
    public final Ingredient repairIngredient;


    DragonToolItemMaterials(int miningLevel, int durability, float miningSpeed, float attackDamage, int enchantability, Item repairIngredient) {
        this.miningLevel      = miningLevel;
        this.durability       = durability;
        this.miningSpeed      = miningSpeed;
        this.attackDamage     = attackDamage;
        this.enchantability   = enchantability;
        this.repairIngredient = repairIngredient != null
                ? Ingredient.ofItems(repairIngredient)
                : Ingredient.empty();
    }


    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }
    @Override
    public int getDurability() {
        return this.durability;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }
    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }
    @Override
    public int getEnchantability() {
        return this.enchantability;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

}
