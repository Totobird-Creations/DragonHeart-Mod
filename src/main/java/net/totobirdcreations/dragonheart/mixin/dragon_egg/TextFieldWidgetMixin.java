package net.totobirdcreations.dragonheart.mixin.dragon_egg;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.egg.DragonEggItem;
import net.totobirdcreations.dragonheart.util.mixin.dragon_egg.DragonEggTextFieldWidgetMixinInterface;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin extends ClickableWidget implements DragonEggTextFieldWidgetMixinInterface {

    @Nullable
    public AnvilScreen anvilScreen = null;


    public TextFieldWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }


    public void setAnvilScreen(@Nullable AnvilScreen screen) {
        this.anvilScreen = screen;
    }


    private boolean isAnvilDragoneggCreative() {
        if (this.anvilScreen != null) {
            ItemStack stack = this.anvilScreen.getScreenHandler().getSlot(0).getStack();
            if (! stack.isEmpty()
                    && stack.getItem() instanceof DragonEggItem egg
                    && egg.isCreative(stack)
            ) {
                return true;
            }
        }
        return false;
    }


    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;write(Ljava/lang/String;)V"
            )
    )
    public void allRedirectWrite(TextFieldWidget widget, String text) {

        // Prevent invalid characters from being entered into the anvil name.
        if (this.isAnvilDragoneggCreative()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < text.length() ; i++) {
                char ch = text.charAt(i);
                if (DragonHeart.HEX_CHARS.indexOf(ch) != -1) {
                    builder.append(ch);
                }
            }
            text = builder.toString();
        }

        widget.write(text);

    }


    @Redirect(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setCursor(I)V"
            )
    )
    public void allRedirectSetCursor(TextFieldWidget widget, int cursor) {
        TextFieldWidgetInterface ithis = (TextFieldWidgetInterface)this;

        // Prevent cursor from going below 1, and fix text.
        if (this.isAnvilDragoneggCreative()) {
            if (ithis.getText().length() < 1) {
                ithis.setTextProperty("#");
            }
            cursor = Math.max(cursor, 1);
        }

        widget.setCursor(cursor);

    }

}
