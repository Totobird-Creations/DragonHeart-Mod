package net.totobirdcreations.dragonheart.item.sword;

public class DragonboneSwordMaterial extends DragonSwordMaterial {
    public static final DragonboneSwordMaterial INSTANCE = new DragonboneSwordMaterial();

    @Override
    public float getAttackDamage() {
        return 15.0f - 1.0f;
    }
}
