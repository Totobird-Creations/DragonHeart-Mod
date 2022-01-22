package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Ice;
import net.totobirdcreations.dragonheart.item.material.SwordMaterial;


public class SwordIce extends Sword {


    public SwordIce(Settings settings, SwordMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Ice.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
