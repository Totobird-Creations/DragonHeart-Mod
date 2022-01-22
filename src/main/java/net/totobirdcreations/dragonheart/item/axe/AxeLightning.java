package net.totobirdcreations.dragonheart.item.axe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Lightning;
import net.totobirdcreations.dragonheart.item.material.AxeMaterial;


public class AxeLightning extends Axe {


    public AxeLightning(Settings settings, AxeMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Lightning.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
