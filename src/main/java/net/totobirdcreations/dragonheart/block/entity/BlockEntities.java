package net.totobirdcreations.dragonheart.block.entity;

import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;


public class BlockEntities {


    public static void register() {
        DragonHeart.LOGGER.info("Registering block entities.");
        DragonBlockEntities.register();
    }


}
