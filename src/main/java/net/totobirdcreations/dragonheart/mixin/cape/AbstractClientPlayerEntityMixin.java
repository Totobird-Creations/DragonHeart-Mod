package net.totobirdcreations.dragonheart.mixin.cape;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }


    @Inject(
            method = "getCapeTexture()Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void mixinGetCapeTexture(CallbackInfoReturnable<Identifier> callback) {
        Identifier v = DragonHeart.Developer.getCape(this.getUuidAsString());
        if (v != null) {
            callback.setReturnValue(v);
        }
    }


    @Inject(
            method = "getElytraTexture()Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void mixinGetElytraTexture(CallbackInfoReturnable<Identifier> callback) {
        Identifier v = DragonHeart.Developer.getElytra(this.getUuidAsString());
        if (v != null) {
            callback.setReturnValue(v);
        }
    }

}
