package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.client.font.TextRenderer;
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
import net.totobirdcreations.dragonheart.item.misc.Dragonegg;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AnvilScreen.class)
public abstract class DragoneggAnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {

    private static final String HEX_CHARS = "012346789abcdefABCDEF";


    public DragoneggAnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }


    @Redirect(
            method = "drawForeground(Lnet/minecraft/client/util/math/MatrixStack;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"
            )
    )
    public int drawForegroundRedirectDrawWithShadow(TextRenderer renderer, MatrixStack matrices, Text text, float x, float y, int textColour) {
        ItemStack stack = this.handler.getSlot(0).getStack();
        if (! (
                stack.getItem() instanceof Dragonegg egg &&
                egg.isCreative(stack)
        )) {
            return renderer.drawWithShadow(matrices, text, x, y, textColour);
        }
        return 0;
    }


    @Inject(
            method = "drawForeground(Lnet/minecraft/client/util/math/MatrixStack;II)V",
            at = @At("TAIL")
    )
    public void drawForegroundInjectTail(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo callback) {
        ItemStack stack = this.handler.getSlot(0).getStack();
        if (
                ! stack.isEmpty() &&
                stack.getItem() instanceof Dragonegg egg &&
                egg.isCreative(stack)
        ) {
            RGBColour colour     = RGBColour.parseString(((DragoneggAnvilScreenInterface)this).getNameField().getText());
            Text      text       = Text.translatable("container.minecraft.anvil.dragonheart.dragonegg.success");
            int       textColour = 8453920;
            if (colour == null) {
                text       = Text.translatable("container.minecraft.anvil.dragonheart.dragonegg.failure");
                textColour = 16736352;
            }
            int k = this.backgroundWidth - 8 - this.textRenderer.getWidth(text) - 2;
            fill(matrices, k - 2, 67, this.backgroundWidth - 8, 79, 1325400064);
            this.textRenderer.drawWithShadow(matrices, text, (float)k, 69.0f, textColour);
        }
    }


    @Inject(
            method = "onSlotUpdate(Lnet/minecraft/screen/ScreenHandler;ILnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL")
    )
    public void onSlotUpdateInjectTail(ScreenHandler handler, int slotId, ItemStack stack, CallbackInfo callback) {
        if (slotId == 0) {
            if (
                    ! stack.isEmpty() &&
                    stack.getItem() instanceof Dragonegg egg &&
                    egg.isCreative(stack)
            ) {
                TextFieldWidget field = ((DragoneggAnvilScreenInterface) this).getNameField();
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
        TextFieldWidget field = ((DragoneggAnvilScreenInterface)this).getNameField();

        if (field.isActive()) {
            ItemStack stack = this.handler.getSlot(0).getStack();
            if (
                    ! stack.isEmpty() &&
                    stack.getItem() instanceof Dragonegg egg &&
                    egg.isCreative(stack)
            ) {

                if (text.length() <= 0) {
                    text = "#ffffff";
                }
                if (text.length() > 7) {
                    text = text.substring(0, 8);
                }
                if (text.charAt(0) != '#') {
                    text = "#" + text.substring(1);
                }
                if (text.length() > 1) {
                    String code = text.substring(1);
                    for (int i = 0; i < code.length(); i++) {
                        if (HEX_CHARS.indexOf(code.charAt(i)) == -1) {
                            code = code.substring(0, i) + "f" + code.substring(i + 1);
                        }
                    }
                    text = "#" + code;
                }

                DragonHeart.LOGGER.info(text);

                DragoneggTextFieldWidgetInterface iField = (DragoneggTextFieldWidgetInterface)field;

                iField.setTextProperty(text);
                this.handler.setNewItemName(text);
                assert this.client != null;
                assert this.client.player != null;
                this.client.player.networkHandler.sendPacket(new RenameItemC2SPacket(text));
                callback.cancel();
            }
        }

    }

}
