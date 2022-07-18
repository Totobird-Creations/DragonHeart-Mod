package net.totobirdcreations.dragonheart.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ItemGroup.class)
public interface ItemGroupMixin {

    @Accessor("icon")
    void setIcon(ItemStack icon);

}
