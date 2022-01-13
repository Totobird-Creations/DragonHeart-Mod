package net.totobirdcreations.dragonsarise.item.sword;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DragonboneSwordItemMaterial implements ToolMaterial {
    public static final DragonboneSwordItemMaterial INSTANCE = new DragonboneSwordItemMaterial();

    @Override
    public int getDurability() {
        return 8000;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 0.0f;
    }
    @Override
    public float getAttackDamage() {
        return 25.0f - 1.0f;
    }
    public float getAttackSpeed() {
        return 1.6f - 4.0f;
    }
    @Override
    public int getMiningLevel() {
        return 0;
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
