package net.totobirdcreations.dragonheart.item.hoe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.dragonevent.Ice;
import net.totobirdcreations.dragonheart.item.material.HoeMaterial;


public class HoeIce extends Hoe {


    public HoeIce(Settings settings, HoeMaterial material) {
        super(settings, material);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Ice.hit(target, attacker, true);
        return super.postHit(stack, target, attacker);
    }


}
