package net.totobirdcreations.dragonsarise.item.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class DragonboneSwordFireItem extends SwordItem {
    public DragonboneSwordFireItem(Settings settings) {
        super(DragonboneSwordItemMaterial.INSTANCE, 0, DragonboneSwordItemMaterial.INSTANCE.getAttackSpeed(), settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(25);
        target.world.playSound(
                null,
                target.getBlockPos(),
                SoundEvents.ITEM_FIRECHARGE_USE,
                SoundCategory.NEUTRAL,
                1f,
                1f
        );
        return super.postHit(stack, target, attacker);
    }
}
