package net.totobirdcreations.dragonheart.mixin.sync;

import net.minecraft.server.ServerMetadata;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.util.mixin.sync.SyncServerMetadataMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ServerMetadata.class)
public class SyncServerMetadataMixin implements SyncServerMetadataMixinInterface {

    Text realDescription;

    public void setRealDescription(Text value) {
        this.realDescription = value;
    }

    public Text getRealDescription() {
        return this.realDescription;
    }

    public Text getModifiedDescription() {
        return ((SyncServerMetadataInterface) this).getDescription();
    }

    @Inject(
            method = "getDescription()Lnet/minecraft/text/Text;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getDescriptionInjectHead(CallbackInfoReturnable<Text> callback) {
        if (getRealDescription() != null) {
            callback.setReturnValue(this.getRealDescription());
        } else {
            callback.setReturnValue(this.getModifiedDescription());
        }
    }

}
