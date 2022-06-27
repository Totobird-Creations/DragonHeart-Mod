package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.totobirdcreations.dragonheart.DragonHeart;


public class ModCommands {


    public static void registerCommand(LiteralArgumentBuilder builder) {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(builder);
        });

    }


    public static void register() {

        DragonHeart.LOGGER.info("Registering commands.");

        registerCommand(DragonManager.getBrigadier());

    }


}
