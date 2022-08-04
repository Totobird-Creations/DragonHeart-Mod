package net.totobirdcreations.dragonheart.mixin.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ItemGroup.class)
public interface ItemGroupInterface {

    @Accessor("icon")
    void setIcon(ItemStack icon);

}
