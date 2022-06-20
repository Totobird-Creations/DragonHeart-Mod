package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.totobirdcreations.dragonheart.DragonHeart;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static net.minecraft.server.command.CommandManager.argument;


public class ModCommands {


    public static void registerCommand(LiteralArgumentBuilder builder) {

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
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
                                                                                literal("SLEEP")
                                                                                    .executes(context -> DragonManagerSetState.sleep(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                        )
                                                                        .then(
                                                                                literal("STAND")
                                                                                        .executes(context -> DragonManagerSetState.stand(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                        )
                                                                        .then(
                                                                                literal("WALK")
                                                                                        .executes(context -> DragonManagerSetState.walk(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                        )
                                                                        .then(
                                                                                literal("FLY")
                                                                                        .executes(context -> DragonManagerSetState.fly(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                        )
                                                                        .then(
                                                                                literal("DIVE")
                                                                                        .executes(context -> DragonManagerSetState.dive(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                        )
                                                        )
                                        )
                        )
        );

    }


}
