package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.DragonEggItem;
import net.totobirdcreations.dragonheart.util.mixin.dragonegg.DragoneggTextFieldWidgetMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;


@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {


    public AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }


    private boolean isAnvilDragoneggCreative() {
        ItemStack stack = this.getScreenHandler().getSlot(0).getStack();
        if (
                ! stack.isEmpty() &&
                stack.getItem() instanceof DragonEggItem egg &&
                egg.isCreative(stack)
        ) {
            return true;
        }
        return false;
    }


    @Inject(
            method = "setup()V",
            at = @At("TAIL")
    )
    public void setupInjectTail(CallbackInfo callback) {
        ((DragoneggTextFieldWidgetMixinInterface)(((AnvilScreenInterface)this).getNameField())).setAnvilScreen((AnvilScreen)(Object)this);
    }


    @Inject(
            method = "onSlotUpdate(Lnet/minecraft/screen/ScreenHandler;ILnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL")
    )
    public void onSlotUpdateInjectTail(ScreenHandler handler, int slotId, ItemStack stack, CallbackInfo callback) {
        if (slotId == 0) {
            if (this.isAnvilDragoneggCreative()) {
                TextFieldWidget field = ((AnvilScreenInterface) this).getNameField();
                field.setText("#ffffff");
                field.setCursorToStart();
                field.setCursorToEnd();
                this.setFocused(field);
            }
        }
    }


    @Inject(
            method = "onRenamed(Ljava/lang/String;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onRenamedInjectHead(String text, CallbackInfo callback) {
        TextFieldWidget field = ((AnvilScreenInterface)this).getNameField();

        ItemStack stack = this.handler.getSlot(0).getStack();
        if (this.isAnvilDragoneggCreative()) {
            TextFieldWidgetInterface ifield = (TextFieldWidgetInterface)field;
            Consumer<String> changedListener = ifield.getChangedListener();
            field.setChangedListener(null);

            // First character `#`.
            if (text.length() < 1) {
                text = "#";
            }
            if (text.charAt(0) != '#') {
                text = "#" + text.substring(1);
            }
            // Max 7 characters.
            if (text.length() > 7) {
                text = text.substring(0, 7);
                if (field.getCursor() > text.length()) {
                    field.setCursor(text.length());
                }
            }
            // Non-first characters hexadecimal.
            if (text.length() > 1) {
                String code = text.substring(1);
                for (int i = 0; i < code.length(); i++) {
                    if (DragonHeart.HEX_CHARS.indexOf(code.charAt(i)) == -1) {
                        code = code.substring(0, i) + "f" + code.substring(i + 1);
                    }
                }
                text = "#" + code;
            }

            ifield.setTextProperty(text);
            this.handler.setNewItemName(text);
            assert this.client != null;
            assert this.client.player != null;
            this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(text));
            field.setChangedListener(changedListener);
            callback.cancel();
        }

    }


    @Inject(
            method = "drawForeground(Lnet/minecraft/client/util/math/MatrixStack;II)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void drawForegroundInjectHead(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo callback) {
        if (this.isAnvilDragoneggCreative()) {
            super.drawForeground(matrices, mouseX, mouseY);
            callback.cancel();
        }
    }

}
