package net.totobirdcreations.dragonheart.entity;



import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;


public class DragonFireEntityModel extends DragonEntityModel {


    @Override
    public Identifier getTextureLocation(DragonEntity object) {

        return new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/fire.png");

    }


    /*@Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

        if (entity instanceof DragonEntity) {
            int colour = entity.getDataTracker().get(entity.COLOUR);
            red   *= ((colour >> 16) & 0xff) / 255.0f;
            green *= ((colour >>  8) & 0xff) / 255.0f;
            blue  *= ((colour      ) & 0xff) / 255.0f;
        }
        float finalRed   = red;
        float finalGreen = green;
        float finalBlue  = blue;
        super.render(matrices, vertices, light, overlay, finalRed, finalGreen, finalBlue, alpha);

    }*/


}
