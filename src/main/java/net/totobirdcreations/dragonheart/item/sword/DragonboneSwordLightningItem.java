package net.totobirdcreations.dragonheart.item.sword;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragonboneSwordLightningItem extends SwordItem {
    public DragonboneSwordLightningItem(Settings settings) {
        super(DragonboneSwordItemMaterial.INSTANCE, 0, DragonboneSwordItemMaterial.INSTANCE.getAttackSpeed(), settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world  = target.getWorld();
        // Strike lightning on target if it has view of the sky
        if (world.isSkyVisible(target.getBlockPos())) {
            LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            entity.setPosition(target.getPos());
            world.spawnEntity(entity);
            // If weather is thunder, kill target instantly
            if (world.isThundering()) {
                target.kill();
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {


        tooltip.add(new TranslatableText("tooltip." + DragonHeart.MOD_ID + ".dragonbone_sword_lightning.0"));
        tooltip.add(new TranslatableText("tooltip." + DragonHeart.MOD_ID + ".dragonbone_sword_lightning.1"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
