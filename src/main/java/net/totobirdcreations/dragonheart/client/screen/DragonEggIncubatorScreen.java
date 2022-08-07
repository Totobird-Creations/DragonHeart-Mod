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
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator.DragoneggIncubatorBlockEntityProperties;
import net.totobirdcreations.dragonheart.screen_handler.DragonEggIncubatorScreenHandler;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;


public class DragonEggIncubatorScreen extends HandledScreen<DragonEggIncubatorScreenHandler> {

    public static final Identifier TEXTURE = new Identifier(DragonHeart.ID, "textures/gui/dragon_egg_incubator.png");


    public DragonEggIncubatorScreen(DragonEggIncubatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    public void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }


    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width  - this.backgroundWidth  ) / 2;
        int y = (this.height - this.backgroundHeight ) / 2;
        this.drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int power    = this.handler.properties.get(DragoneggIncubatorBlockEntityProperties.POWER     );
        int maxPower = this.handler.properties.get(DragoneggIncubatorBlockEntityProperties.MAX_POWER );
        if (power > 0 && maxPower > 0) {
            RGBColour colour = new RGBColour(this.handler.properties.get(DragonForgeCoreBlockEntityProperties.COLOUR));
            RenderSystem.setShaderColor(colour.r, colour.g, colour.b, 1.0f);
            this.drawTexture(
                    matrices, x + 84, y + 18, 176, 0,
                    8,
                    (int)(Math.ceil(((float)power) / ((float)maxPower) * 26))
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
