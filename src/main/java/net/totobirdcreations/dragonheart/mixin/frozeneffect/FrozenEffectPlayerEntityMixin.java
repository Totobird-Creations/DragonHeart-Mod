package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.util.effect.FrozenEffectLivingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerEntity.class)
public abstract class FrozenEffectPlayerEntityMixin extends Entity {

    public FrozenEffectPlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void attack(Entity target, CallbackInfo callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.cancel();
        }
    }


    @Inject(
            method = "isBlockBreakingRestricted(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/GameMode;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isBlockBreakingRestricted(World world, BlockPos pos, GameMode gamemode, CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(true);
        }
    }


    @Inject(
            method = "canPlaceOn(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void canPlaceOn(BlockPos pos, Direction facing, ItemStack stack, CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(false);
        }
    }


    @Inject(
            method = "canModifyBlocks()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void canModifyBlocks(CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(false);
        }
    }


    @Inject(
            method = "shouldCancelInteraction()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void shouldCancelInteraction(CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(true);
        }
    }


    @Inject(
            method = "interact(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(ActionResult.FAIL);
        }
    }


    @Inject(
            method = "canEquip(Lnet/minecraft/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void canEquip(ItemStack stack, CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(false);
        }
    }


    @Inject(
            method = "canConsume(Z)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void canConsume(boolean ignoreHunger, CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(false);
        }
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/ItemCooldownManager;update()V"
            )
    )
    public void tickItemCooldownManagerUpdate(ItemCooldownManager object) {
        if (! ((FrozenEffectLivingEntityInterface)this).isIced()) {
            object.update();
        }
    }

}
