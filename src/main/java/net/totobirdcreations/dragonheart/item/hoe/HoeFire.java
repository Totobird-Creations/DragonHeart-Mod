package net.totobirdcreations.dragonheart.item.hoe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.dragonevent.Fire;
import net.totobirdcreations.dragonheart.item.material.HoeMaterial;


public class HoeFire extends Hoe {


    public HoeFire(Settings settings, HoeMaterial material) {
        super(settings, material);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Fire.hit(target, true);
        return super.postHit(stack, target, attacker);
    }


}
