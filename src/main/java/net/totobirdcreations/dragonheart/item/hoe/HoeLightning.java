package net.totobirdcreations.dragonheart.item.hoe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Lightning;
import net.totobirdcreations.dragonheart.item.material.HoeMaterial;


public class HoeLightning extends Hoe {


    public HoeLightning(Settings settings, HoeMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Lightning.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
