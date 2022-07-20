package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ClickableWidget.class)
public interface DragoneggClickableWidgetInterface {

    @Accessor("visible")
    boolean isVisible();

    @Accessor("focused")
    boolean isFocused();

}
