package net.totobirdcreations.dragonheart.mixin.dragon_egg;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Consumer;


@Mixin(TextFieldWidget.class)
public interface TextFieldWidgetInterface {

    @Accessor("text")
    void setTextProperty(String text);

    @Accessor("changedListener")
    Consumer<String> getChangedListener();

    @Accessor("text")
    String getText();

}
