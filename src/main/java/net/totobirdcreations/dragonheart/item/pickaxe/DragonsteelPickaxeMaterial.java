package net.totobirdcreations.dragonheart.item.pickaxe;

public class DragonsteelPickaxeMaterial extends DragonPickaxeMaterial {
    public static final DragonsteelPickaxeMaterial INSTANCE = new DragonsteelPickaxeMaterial();

    @Override
    public float getMiningSpeedMultiplier() {
        return 15.0f;
    }
}
