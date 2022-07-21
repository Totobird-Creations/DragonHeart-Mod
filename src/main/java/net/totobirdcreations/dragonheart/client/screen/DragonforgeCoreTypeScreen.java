package net.totobirdcreations.dragonheart.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;



public class DragonforgeCoreTypeScreen extends HandledScreen<DragonforgeCoreTypeScreenHandler> {


    public DragonforgeCoreTypeScreen(DragonforgeCoreTypeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    public void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }


    @Override
    public Text getTitle() {
        return this.handler.getTitle();
    }


    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader        (GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor   (1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture (0, this.handler.getScreenTexture());
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (handler.isCrafting()) {
            int progress = handler.getScaledProgress();
            this.drawTexture(matrices, x + 80, y + 35 + (14 - progress), 176, 14 - progress, 16, progress);
        }
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }


}
