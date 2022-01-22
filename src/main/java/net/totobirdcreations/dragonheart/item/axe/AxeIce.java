package net.totobirdcreations.dragonheart.item.axe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Ice;
import net.totobirdcreations.dragonheart.item.material.AxeMaterial;


public class AxeIce extends Axe {


    public AxeIce(Settings settings, AxeMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Ice.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
