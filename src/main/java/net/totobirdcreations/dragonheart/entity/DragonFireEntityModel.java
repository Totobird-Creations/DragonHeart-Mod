package net.totobirdcreations.dragonheart.entity;



import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;


public class DragonFireEntityModel extends DragonEntityModel {

    public static Identifier TEXTURE = new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/fire.png");

    @Override
    public Identifier getTextureResource(DragonEntity object) {

        return TEXTURE;

    }


}
