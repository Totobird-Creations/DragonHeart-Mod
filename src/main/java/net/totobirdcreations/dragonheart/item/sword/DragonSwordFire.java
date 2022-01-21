package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.totobirdcreations.dragonheart.item.dragonevent.Fire;

public class DragonSwordFire extends DragonSword {
    public DragonSwordFire(Settings settings, DragonSwordMaterial material) {
        super(settings, material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Fire.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
