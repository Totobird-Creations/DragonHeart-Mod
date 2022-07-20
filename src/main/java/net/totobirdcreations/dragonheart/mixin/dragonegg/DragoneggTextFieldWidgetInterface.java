package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(TextFieldWidget.class)
public interface DragoneggTextFieldWidgetInterface {

    @Accessor("text")
    void setTextProperty(String text);

}
