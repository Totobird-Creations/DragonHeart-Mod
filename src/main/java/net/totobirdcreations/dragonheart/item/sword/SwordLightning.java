package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.totobirdcreations.dragonheart.item.dragonevent.Lightning;



public class SwordLightning extends Sword {


    public SwordLightning(Settings settings, SwordMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Lightning.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
