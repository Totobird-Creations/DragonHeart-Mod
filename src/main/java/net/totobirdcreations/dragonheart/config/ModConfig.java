package net.totobirdcreations.dragonheart.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;


public class ModConfig {

    public static void register() {
        AutoConfig.register(ConfigData.class, Toml4jConfigSerializer::new);
    }

}
