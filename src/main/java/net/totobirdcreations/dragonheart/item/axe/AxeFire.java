package net.totobirdcreations.dragonheart.item.axe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Fire;
import net.totobirdcreations.dragonheart.item.material.AxeMaterial;


public class AxeFire extends Axe {


    public AxeFire(Settings settings, AxeMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Fire.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
