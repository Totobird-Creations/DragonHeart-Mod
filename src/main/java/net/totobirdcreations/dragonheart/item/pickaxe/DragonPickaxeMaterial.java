package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DragonPickaxeMaterial implements ToolMaterial {

    @Override
    public int getDurability() {
        return 8000;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 10.0f;
    }
    @Override
    public float getAttackDamage() {
        return 25.0f - 1.0f;
    }
    public float getAttackSpeed() {
        return -2.8f;
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
