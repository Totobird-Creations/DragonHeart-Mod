package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.totobirdcreations.dragonheart.item.dragon.DragoneggItem;
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
public abstract class DragoneggAnvilScreenHandlerMixin extends ForgingScreenHandler {

    public DragoneggAnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }


    @Inject(
            method = "updateResult()V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void updateResultInject(CallbackInfo callback) {
        DragoneggAnvilScreenHandlerInterface ithis = ((DragoneggAnvilScreenHandlerInterface) this);

        ItemStack stack = this.getSlot(0).getStack();
        if (
                ! stack.isEmpty() &&
                stack.getItem() instanceof DragoneggItem egg &&
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
        DragoneggAnvilScreenHandlerInterface   ithis  = ((DragoneggAnvilScreenHandlerInterface) this);
        DragoneggForgingScreenHandlerInterface ipthis = ((DragoneggForgingScreenHandlerInterface) this);
        if (
                ! stack.isEmpty() &&
                stack.getItem() instanceof DragoneggItem egg &&
                egg.isCreative(stack)
        ) {
            RGBColour colour = RGBColour.parseString(ithis.getNewItemName());
            if (colour != null) {
                stack = new ItemStack(DragonItems.DRAGONEGG);
                DragonColouredItem.setColour(stack, colour.asInt());
                NbtCompound nbt = ipthis.getInput().getStack(0).getOrCreateNbt();
                nbt.putString("dragon", NbtHelper.getItemDragonType(nbt).toString());
                output.setStack(slot, stack);
            }

        } else {
            output.setStack(slot, stack);
        }
    }

}
