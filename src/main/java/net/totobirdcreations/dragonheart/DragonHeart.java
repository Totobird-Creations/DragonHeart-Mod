package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.block.BlockTags;
import net.totobirdcreations.dragonheart.block.Blocks;
import net.totobirdcreations.dragonheart.command.Commands;
import net.totobirdcreations.dragonheart.config.Config;
import net.totobirdcreations.dragonheart.effect.StatusEffects;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.event.EventHandlers;
import net.totobirdcreations.dragonheart.gamerule.Gamerules;
import net.totobirdcreations.dragonheart.item.Items;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;
import net.totobirdcreations.dragonheart.particle.Particles;
import net.totobirdcreations.dragonheart.resource.Resources;
import net.totobirdcreations.dragonheart.screen_handler.ScreenHandlers;
import net.totobirdcreations.dragonheart.sound.SoundEvents;
import net.totobirdcreations.dragonheart.structure.Structures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Properties;


public class DragonHeart implements ModInitializer {

	public static String HEX_CHARS = "012346789abcdefABCDEF";

	public static String ID;
	public static String NAME;
	public static String VERSION;
	static {
		Properties properties = new Properties();
		try {
			properties.load(DragonHeart.class.getResourceAsStream("/mod.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ID      = properties.getProperty("id"      );
		NAME    = properties.getProperty("name"    );
		VERSION = properties.getProperty("version" );
	}
	public static Logger LOGGER = LogManager.getLogger(ID);

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
				return new Identifier(ID, "textures/entity/player/" + type.toString().toLowerCase() + "/developer/" + developer.name().toLowerCase() + ".png");
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

		LOGGER.debug("Initializing.");

		DEVENV = FabricLoader.getInstance().isDevelopmentEnvironment();

		/*if (DEVENV) {
			LOGGER.info("Suppressing GeckoLibMod.");
		}
		GeckoLibMod.DISABLE_IN_DEV = true;*/
		GeckoLib.initialize();

		/*LooseEndManager.getInstance().register(MOD_ID, MOD_NAME, MOD_VERSION.split("-")[0])
				.whenClientJoins(LooseEnd.Condition.REQUIRED)
				.whenJoinServer(LooseEnd.Condition.REQUIRED);*/

		Config.register();
		Blocks.register();
		SoundEvents.register();
		Items.register();
		StatusEffects.register();
		Entities.register();
		Commands.register();
		Gamerules.register();
		ScreenHandlers.register();
		Resources.register();
		EventHandlers.register();
		Particles.register();
		Structures.register();

		LOGGER.info("Initialized.");

	}


}
