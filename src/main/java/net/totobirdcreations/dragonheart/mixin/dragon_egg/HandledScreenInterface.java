package net.totobirdcreations.dragonheart.mixin.dragon_egg;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(HandledScreen.class)
public interface HandledScreenInterface<T extends ScreenHandler> {

    @Accessor("handler")
    T getHandler();

}
