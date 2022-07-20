package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(AnvilScreen.class)
public interface DragoneggAnvilScreenInterface {

    @Accessor("nameField")
    TextFieldWidget getNameField();

}
