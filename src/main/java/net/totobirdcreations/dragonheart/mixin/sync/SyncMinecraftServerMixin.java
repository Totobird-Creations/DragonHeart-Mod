package net.totobirdcreations.dragonheart.mixin.sync;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerMetadata;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.sync.ModSync;
import net.totobirdcreations.dragonheart.util.mixin.sync.SyncServerMetadataMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public class SyncMinecraftServerMixin {

    @Redirect(
            method = "runServer()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/ServerMetadata;setDescription(Lnet/minecraft/text/Text;)V"
            )
    )
    public void runServerRedirectSetDescription(ServerMetadata metadata, Text description) {
        ((SyncServerMetadataMixinInterface)metadata).setRealDescription(description);
        metadata.setDescription(ModSync.getMetadataDescription_clientMissing());
    }

}
