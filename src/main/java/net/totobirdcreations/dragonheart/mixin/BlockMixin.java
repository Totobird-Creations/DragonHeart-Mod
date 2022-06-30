package net.totobirdcreations.dragonheart.mixin;


import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(
            method = "getSlipperiness",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getSlipperiness(CallbackInfoReturnable callback) {
        callback.setReturnValue(0.989f);
    }

}
