package net.totobirdcreations.dragonheart.item.pickaxe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.totobirdcreations.dragonheart.item.dragonevent.Ice;



public class PickaxeIce extends Pickaxe {


    public PickaxeIce(Settings settings, PickaxeMaterial material) {

        super(settings, material);

    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        Ice.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);

    }


}
