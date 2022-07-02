package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Keyboard.class)
public abstract class FrozenEffectKeyboardMixin {

    @Inject(
            method = "onKey",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/KeyBinding;onKeyPressed(Lnet/minecraft/client/util/InputUtil$Key;)V"
            ),
            cancellable = true
    )
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo callback) {
        boolean detected = (
                MinecraftClient.getInstance().options.forwardKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.leftKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.backKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.rightKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.jumpKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.sneakKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.sprintKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.swapHandsKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.dropKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.useKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.attackKey.matchesKey(key, scancode) ||
                MinecraftClient.getInstance().options.pickItemKey.matchesKey(key, scancode)
        );
        if (! detected) {
            KeyBinding[] keys = MinecraftClient.getInstance().options.hotbarKeys;
            for (KeyBinding keyBinding : keys) {
                if (keyBinding.matchesKey(key, scancode)) {
                    detected = true;
                    break;
                }
            }
        }
        if (detected) {
            callback.cancel();
        }
    }

}
