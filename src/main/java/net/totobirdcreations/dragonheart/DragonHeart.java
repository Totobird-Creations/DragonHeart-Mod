package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.totobirdcreations.dragonheart.block.ModBlockTags;
import net.totobirdcreations.dragonheart.block.ModBlocks;
import net.totobirdcreations.dragonheart.command.ModCommands;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.gamerule.ModGamerules;
import net.totobirdcreations.dragonheart.item.ModItems;
import net.totobirdcreations.dragonheart.item.group.ModItemGroups;
import net.totobirdcreations.dragonheart.recipe.ModRecipes;
import net.totobirdcreations.dragonheart.soundevent.ModSoundEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;


public class DragonHeart implements ModInitializer {


	public static final String  MOD_ID = "dragonheart";
	public static final Logger  LOGGER = LogManager.getLogger(MOD_ID);
	public static       boolean DEVENV;


	@Override
	public void onInitialize() {

		LOGGER.info("Initializing.");

		DEVENV = FabricLoader.getInstance().isDevelopmentEnvironment();

		if (DEVENV) {
			LOGGER.info("Suppressing GeckoLibMod.");
			GeckoLibMod.DISABLE_IN_DEV = true;
			GeckoLib.initialize();
		}

		ModBlocks         .register();
		ModBlockTags      .register();
		ModSoundEvents    .register();
		ModItems          .register();
		ModItemGroups     .register();
		ModStatusEffects  .register();
		ModRecipes        .register();
		ModEntities       .register();
		ModCommands       .register();
		ModGamerules      .register();

		LOGGER.info("Initialized.");

	}


}
