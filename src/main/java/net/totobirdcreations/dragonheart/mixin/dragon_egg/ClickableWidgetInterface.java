package net.totobirdcreations.dragonheart.mixin.dragon_egg;

import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ClickableWidget.class)
public interface ClickableWidgetInterface {

    @Accessor("visible")
    boolean isVisible();

    @Accessor("focused")
    boolean isFocused();

}
