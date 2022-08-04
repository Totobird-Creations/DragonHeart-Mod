package net.totobirdcreations.dragonheart.client.block;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonGriefedBlockEntity;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class DragonGriefedBlockEntityRenderer implements BlockEntityRenderer<DragonGriefedBlockEntity> {

    public AllSideCuboid cuboid = new AllSideCuboid(
            0, 0,                // UV
            0.0f, 0.0f, 0.0f,    // Origin
            16.0f, 16.0f, 16.0f, // Size
            0.0f, 0.0f, 0.0f,    // Extra
            false,               // Mirror
            16, 16               // Texture Size
    );


    public DragonGriefedBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }


    @Override
    public void render(DragonGriefedBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {
        // Get the texture and get the vertex consumer.
        Identifier path = entity.dragon.equals(NbtHelper.EMPTY_TYPE)
                ? new Identifier(DragonHeart.ID, "textures/misc/empty.png")
                : new Identifier(entity.dragon.getNamespace(), "textures/block/dragon_griefed/" + entity.dragon.getPath() + ".png");
        VertexConsumer consumer = provider.getBuffer(RenderLayer.getEntitySolid(path));
        // Generating the mesh.
        matrices.push();
        cuboid.renderCuboid(matrices.peek(), consumer, overlay, entity);
        matrices.pop();
    }

}
