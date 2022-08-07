package net.totobirdcreations.dragonheart.mixin.dragon_egg;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.totobirdcreations.dragonheart.item.dragon.egg.DragonEggItem;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;


@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }


    @Inject(
            method = "updateResult()V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void updateResultInject(CallbackInfo callback) {
        AnvilScreenHandlerInterface ithis = ((AnvilScreenHandlerInterface) this);

        ItemStack stack = this.getSlot(0).getStack();
        if (
                ! stack.isEmpty() &&
                stack.getItem() instanceof DragonEggItem egg &&
                egg.isCreative(stack)
        ) {
            RGBColour colour = RGBColour.parseString(ithis.getNewItemName());
            if (colour == null) {
                this.output.setStack(0, ItemStack.EMPTY);
                ithis.getLevelCost().set(0);
                callback.cancel();
            }
        }
    }


    @Redirect(
            method = "updateResult()V",
            at = @At(
                    value  = "INVOKE",
                    target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"
            )
    )
    public void updateResultRedirectSetStack(CraftingResultInventory output, int slot, ItemStack stack) {
        AnvilScreenHandlerInterface   ithis  = ((AnvilScreenHandlerInterface) this);
        ForgingScreenHandlerInterface ipthis = ((ForgingScreenHandlerInterface) this);
        if (
                ! stack.isEmpty() &&
                stack.getItem() instanceof DragonEggItem egg &&
                egg.isCreative(stack)
        ) {
            RGBColour colour = RGBColour.parseString(ithis.getNewItemName());
            if (colour != null) {
                ItemStack newStack = new ItemStack(DragonItems.DRAGON_EGG);
                DragonColouredItem.setColour(newStack, colour.asInt());
                NbtHelper.setItemDragonType(newStack,
                        NbtHelper.getItemDragonType(stack)
                );
                output.setStack(slot, newStack);
            }

        } else {
            output.setStack(slot, stack);
        }
    }

}
