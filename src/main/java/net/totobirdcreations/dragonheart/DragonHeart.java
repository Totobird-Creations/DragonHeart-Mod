package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.block.ModBlockTags;
import net.totobirdcreations.dragonheart.block.ModBlocks;
import net.totobirdcreations.dragonheart.command.ModCommands;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.gamerule.ModGamerules;
import net.totobirdcreations.dragonheart.item.ModItems;
import net.totobirdcreations.dragonheart.item.group.ModItemGroups;
import net.totobirdcreations.dragonheart.potion.ModPotions;
import net.totobirdcreations.dragonheart.recipe.ModRecipes;
import net.totobirdcreations.dragonheart.sound.ModSoundEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nullable;


public class DragonHeart implements ModInitializer {


	public static String  MOD_ID = "dragonheart";
	public static Logger  LOGGER = LogManager.getLogger(MOD_ID);
	public static boolean DEVENV;

	public enum Developer {
		PROGRAMMER,
		ARTIST;

		public enum Type {
			CAPE,
			ELYTRA
		}

		@Nullable
		public static Developer getDeveloper(String uuid) {
			return switch (uuid) {
				case "bd9e79ad-1065-4045-8b08-87346cff42a7" -> PROGRAMMER; // TotobirdCreation
				case "79f8cb8b-83a0-43ca-a8d1-cc37b07c2627" -> ARTIST;     // TheBalec
				default                                     -> null;
			};
		}

		@Nullable
		public static Identifier getTexture(String uuid, Type type) {
			Developer developer = getDeveloper(uuid);
			if (DragonHeart.DEVENV && developer == null) {
				developer = Developer.PROGRAMMER;
			}
			if (developer != null) {
				return new Identifier(MOD_ID, "textures/entity/player/" + type.toString().toLowerCase() + "/developer/" + developer.name().toLowerCase() + ".png");
			}
			return null;
		}

		@Nullable
		public static Identifier getCape(String uuid) {
			return getTexture(uuid, Type.CAPE);
		}

		@Nullable
		public static Identifier getElytra(String uuid) {
			return getTexture(uuid, Type.ELYTRA);
		}

	}


	@Override
	public void onInitialize() {

		LOGGER.info("Initializing.");

		DEVENV = FabricLoader.getInstance().isDevelopmentEnvironment();

		if (DEVENV) {
			LOGGER.info("Suppressing GeckoLibMod.");
			GeckoLibMod.DISABLE_IN_DEV = true;
		}
		GeckoLib.initialize();

		ModBlocks        .register();
		ModBlockTags     .register();
		ModSoundEvents   .register();
		ModItemGroups    .register();
		ModItems         .register();
		ModStatusEffects .register();
		ModRecipes       .register();
		ModEntities      .register();
		ModCommands      .register();
		ModGamerules     .register();
		ModPotions       .register();

		LOGGER.info("Initialized.");

	}


}
