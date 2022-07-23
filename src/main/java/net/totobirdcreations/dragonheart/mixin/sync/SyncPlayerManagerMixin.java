package net.totobirdcreations.dragonheart.mixin.sync;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.totobirdcreations.dragonheart.sync.ModSync;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerManager.class)
public class SyncPlayerManagerMixin {

    @Inject(
            method = "onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onPlayerConnectInjectHead(ClientConnection connection, ServerPlayerEntity player, CallbackInfo callback) {
        ModSync.onClientJoinedFailure(connection);
        callback.cancel();
    }

}
