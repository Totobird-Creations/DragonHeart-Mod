package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DragonbonePickaxeMaterial extends DragonPickaxeMaterial {
    public static final DragonbonePickaxeMaterial INSTANCE = new DragonbonePickaxeMaterial();

    @Override
    public float getMiningSpeedMultiplier() {
        return 10.0f;
    }
}
