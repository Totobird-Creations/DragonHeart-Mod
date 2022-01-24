package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.dragonevent.Fire;
import net.totobirdcreations.dragonheart.item.material.PickaxeMaterial;


public class PickaxeFire extends Pickaxe {


    public PickaxeFire(Settings settings, PickaxeMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Fire.hit(stack, target, attacker, true);
        return super.postHit(stack, target, attacker);

    }


}
