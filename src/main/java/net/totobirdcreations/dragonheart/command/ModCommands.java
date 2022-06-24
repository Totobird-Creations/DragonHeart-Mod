package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.totobirdcreations.dragonheart.DragonHeart;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static net.minecraft.server.command.CommandManager.argument;


public class ModCommands {


    public static void registerCommand(LiteralArgumentBuilder builder) {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(builder);
        });

    }


    public static void register() {

        DragonHeart.LOGGER.info("Registering commands.");

        registerCommand(
                literal("dragonmanager")
                        .requires(source -> ((CommandSource)source).hasPermissionLevel(2))
                        .then(
                                literal("set")
                                        .then(
                                                literal("state")
                                                        .then(
                                                                ((RequiredArgumentBuilder)argument("target", EntityArgumentType.entity()))
                                                                        .then (
                                                                                literal("NEST")
                                                                                    .executes(context -> DragonManagerSetState.nest(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                        )
                                                        )
                                        )
                        )
        );

    }


}
