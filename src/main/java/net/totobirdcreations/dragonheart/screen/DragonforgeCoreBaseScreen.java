package net.totobirdcreations.dragonheart.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;



public class DragonforgeCoreBaseScreen extends HandledScreen<DragonforgeCoreBaseScreenHandler> {


    public static final Identifier TEXTURE = new Identifier(DragonHeart.MOD_ID, "textures/gui/dragonforge_core_base.png");


    public DragonforgeCoreBaseScreen(DragonforgeCoreBaseScreenHandler handler, PlayerInventory inventory, Text title) {

        super(handler, inventory, title);

    }


    @Override
    public void init() {

        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

    }


    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        RenderSystem.setShader        (GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor   (1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture (0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (handler.isConverting()) {
            int progress       = handler.getScaledProgress();
            int conversionMode = handler.getConversionMode();
            this.drawTexture(matrices, x + 68, y + 24 + (40 - progress), 176, 40 * (conversionMode) - progress, 40, progress);
        }

    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

    }


}
