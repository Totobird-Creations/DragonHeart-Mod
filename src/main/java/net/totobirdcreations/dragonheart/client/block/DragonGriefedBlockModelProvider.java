package net.totobirdcreations.dragonheart.client.block;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;


public class DragonGriefedBlockModelProvider implements ModelResourceProvider {

    public static Identifier MODEL = new Identifier(DragonHeart.ID, "block/dragon_griefed");


    @Override
    public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext context) throws ModelProviderException {
        if (identifier.equals(MODEL)) {
            return new DragonGriefedBlockModel();
        } else {
            return null;
        }
    }

}
