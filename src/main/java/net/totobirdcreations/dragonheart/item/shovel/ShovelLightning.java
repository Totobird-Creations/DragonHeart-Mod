package net.totobirdcreations.dragonheart.item.shovel;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.item.dragonevent.Lightning;
import net.totobirdcreations.dragonheart.item.material.ShovelMaterial;


public class ShovelLightning extends Shovel {


    public ShovelLightning(Settings settings, ShovelMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Lightning.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
