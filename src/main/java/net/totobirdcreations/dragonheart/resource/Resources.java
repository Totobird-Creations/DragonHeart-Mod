package net.totobirdcreations.dragonheart.resource;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.resource.recipe.RecipeResources;

public class Resources {

    public static void register() {
        DragonHeart.LOGGER.debug("Registering resources.");

        RecipeResources.register();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(DragonResourceLoader.INSTANCE);
    }

}
