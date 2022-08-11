package net.totobirdcreations.dragonheart.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntityProperties;
import net.totobirdcreations.dragonheart.screen_handler.DragonForgeCoreScreenHandler;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;


public class DragonForgeCoreScreen extends HandledScreen<DragonForgeCoreScreenHandler> {

    public static final Identifier TEXTURE_BASE = new Identifier(DragonHeart.ID, "textures/gui/dragon_forge_core_base.png" );
    public static final Identifier TEXTURE_TYPE = new Identifier(DragonHeart.ID, "textures/gui/dragon_forge_core.png"      );


    public DragonForgeCoreScreen(DragonForgeCoreScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    public void init() {
        super.init();
        this.playerInventoryTitleY += 3;
    }


    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, this.handler.properties.get(
                DragonForgeCoreBlockEntityProperties.HAS_TYPE) > 0
                ? TEXTURE_TYPE
                : TEXTURE_BASE
        );
        int x = (this.width  - this.backgroundWidth  ) / 2;
        int y = (this.height - this.backgroundHeight ) / 2;
        this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        int progress    = this.handler.properties.get(DragonForgeCoreBlockEntityProperties.PROGRESS     );
        int maxProgress = this.handler.properties.get(DragonForgeCoreBlockEntityProperties.MAX_PROGRESS );
        if (progress > 0 && maxProgress > 0) {
            RGBColour colour = new RGBColour(this.handler.properties.get(DragonForgeCoreBlockEntityProperties.COLOUR));
            RenderSystem.setShaderColor(colour.r, colour.g, colour.b, 1.0f);
            this.drawTexture(
                    matrices, x + 70, y + 24, 176, 0,
                    (int)(Math.ceil(((float)progress) / ((float)maxProgress) * 36)),
                    43
            );
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        for (int i = 0; i < DragonForgeCoreBlockEntity.INVENTORY_SIZE; i++) {
            Slot slot = this.handler.getSlot(i);
            if (slot.getStack().isEmpty()) {
                this.drawTexture(
                        matrices,
                        x + slot.x, y + slot.y,
                        240, 16 * slot.getIndex(),
                        16, 16
                );
            }
        }
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

}
