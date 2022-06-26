package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.totobirdcreations.dragonheart.util.FrozenEffectLivingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.JTextComponent;


@Mixin(Mouse.class)
public abstract class FrozenEffectMouseMixin {

    @Shadow public abstract boolean isCursorLocked();

    @Redirect(
            method = "updateMouse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"
            )
    )
    public void changeLookDirection(ClientPlayerEntity entity, double cursorDeltaX, double cursorDeltaY) {
        if (! ((FrozenEffectLivingEntityInterface)entity).isIced()) {
            entity.changeLookDirection(cursorDeltaX, cursorDeltaY);
        }
    }


    @Inject(
            method = "onMouseButton(JIII)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onMouseButton(long window, int button, int action, int mods, CallbackInfo callback) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && ((FrozenEffectLivingEntityInterface) player).isIced() && isCursorLocked()) {
            callback.cancel();
        }
    }


    @Inject(
            method = "onMouseScroll(JDD)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callback) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && ((FrozenEffectLivingEntityInterface) player).isIced() && isCursorLocked()) {
            callback.cancel();
        }
    }

}