package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ModInitializer;
import net.totobirdcreations.dragonheart.block.ModBlocks;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;
import net.totobirdcreations.dragonheart.item.ModItems;
import net.totobirdcreations.dragonheart.item.group.ModItemGroups;
import net.totobirdcreations.dragonheart.recipe.ModRecipes;
import net.totobirdcreations.dragonheart.soundevent.ModSoundEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class DragonHeart implements ModInitializer {


	public static final String MOD_ID = "dragonheart";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		LOGGER.info("Initializing.");

		ModBlocks         .register();
		ModSoundEvents    .register();
		ModItems          .register();
		ModItemGroups     .register();
		ModStatusEffects  .register();
		ModRecipes        .register();

		LOGGER.info("Initialized.");

	}


}
