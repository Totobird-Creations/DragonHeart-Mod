package net.totobirdcreations.dragonheart.item.sword;

public class DragonsteelSwordMaterial extends DragonSwordMaterial {
    public static final DragonsteelSwordMaterial INSTANCE = new DragonsteelSwordMaterial();

    @Override
    public float getAttackDamage() {
        return 25.0f - 1.0f;
    }
}
