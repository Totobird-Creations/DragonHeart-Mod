package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(HandledScreen.class)
public interface DragoneggHandledScreenInterface<T extends ScreenHandler> {

    @Accessor("handler")
    T getHandler();

}
