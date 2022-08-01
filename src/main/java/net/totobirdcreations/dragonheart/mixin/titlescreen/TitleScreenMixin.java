package net.totobirdcreations.dragonheart.mixin.titlescreen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.totobirdcreations.dragonheart.DragonHeart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {


    public TitleScreenMixin(Text title) {
        super(title);
    }


    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V",
            at = @At("TAIL")
    )
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta, CallbackInfo callback) {
        String text = Text.translatable("titlescreen." + DragonHeart.ID + ".notice").getString();

        boolean doBackgroundFade    = ((TitleScreenInterface)this).getDoBackgroundFade();
        long    backgroundFadeStart = ((TitleScreenInterface)this).getBackgroundFadeStart();

        float m1 = doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - backgroundFadeStart) / 1000.0F : 1.0F;
        float m0 = doBackgroundFade ? MathHelper.clamp(m1 - 1.0F, 0.0F, 1.0F) : 1.0F;
        int   m = MathHelper.ceil(m0 * 255.0F) << 24;
        drawStringWithShadow(stack, this.textRenderer, text, 2, this.height - 20, 16777215 | m);
    }

}
