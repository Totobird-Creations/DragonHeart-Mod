package net.totobirdcreations.dragonheart.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.totobirdcreations.dragonheart.DragonHeart;


public class Config {

    public static ConfigData CONFIG = AutoConfig.register(ConfigData.class, Toml4jConfigSerializer::new).get();

    public static void register() {
        DragonHeart.LOGGER.info("Registering config.");
    }

}
