package net.totobirdcreations.dragonheart.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;


public class DragonFireEntityModel extends EntityModel<DragonFireEntity> {


    public final ModelPart base;

    public DragonFireEntity entity;


    public DragonFireEntityModel(ModelPart modelPart) {

        this.base = modelPart.getChild(EntityModelPartNames.CUBE);

    }


    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-6f, 12f, -6f, 12f, 12f, 12f), ModelTransform.pivot(0f, 0f, 0f));

        return TexturedModelData.of(modelData, 64, 64);

    }


    @Override
    public void setAngles(DragonFireEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        this.entity = entity;

    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

        if (entity instanceof DragonEntity) {
            int colour = entity.getDataTracker().get(entity.COLOUR);
            red   *= ((colour >> 16) & 0xff) / 255.0f;
            green *= ((colour >>  8) & 0xff) / 255.0f;
            blue  *= ((colour      ) & 0xff) / 255.0f;
        }

        float finalRed = red;
        float finalGreen = green;
        float finalBlue = blue;
        ImmutableList.of(this.base).forEach((renderer) -> {
            renderer.render(matrices, vertices, light, overlay, finalRed, finalGreen, finalBlue, alpha);
        });

    }


    public void setRotationAngle(ModelPart bone, float x, float y, float z) {

        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;

    }


}
