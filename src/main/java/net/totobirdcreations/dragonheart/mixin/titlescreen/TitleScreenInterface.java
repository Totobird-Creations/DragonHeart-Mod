package net.totobirdcreations.dragonheart.mixin.titlescreen;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(TitleScreen.class)
public interface TitleScreenInterface {

    @Accessor("doBackgroundFade")
    boolean getDoBackgroundFade();

    @Accessor("backgroundFadeStart")
    long getBackgroundFadeStart();

}
