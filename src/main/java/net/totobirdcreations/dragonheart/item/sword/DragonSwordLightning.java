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
import net.totobirdcreations.dragonheart.item.dragonevent.Lightning;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragonSwordLightning extends DragonSword {
    public DragonSwordLightning(Settings settings, DragonSwordMaterial material) {
        super(settings, material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Lightning.hit(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
