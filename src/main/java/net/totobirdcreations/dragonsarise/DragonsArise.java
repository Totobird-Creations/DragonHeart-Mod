package net.totobirdcreations.dragonsarise;

import net.fabricmc.api.ModInitializer;
import net.totobirdcreations.dragonsarise.block.ModBlocks;
import net.totobirdcreations.dragonsarise.effect.FrozenEffect;
import net.totobirdcreations.dragonsarise.effect.ModEffects;
import net.totobirdcreations.dragonsarise.item.ModItems;
import net.totobirdcreations.dragonsarise.screen.ModScreenHandlers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonsArise implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "dragonsarise";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing.");

		ModItems.register();
		ModBlocks.register();
		ModEffects.register();
		ModScreenHandlers.register();

		LOGGER.info("Initialized.");
	}
}
