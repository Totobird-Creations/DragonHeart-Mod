package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Fire;



public class SwordFire extends Sword {


    public SwordFire(Settings settings, SwordMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Fire.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
