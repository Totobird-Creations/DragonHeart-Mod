package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.entity.DragonEntity;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static net.minecraft.server.command.CommandManager.argument;


public class DragonManager {


    public static LiteralArgumentBuilder getBrigadier() {
        return literal("dragonmanager")
                .requires(source -> ((CommandSource)source).hasPermissionLevel(2))
                .then(
                        ((RequiredArgumentBuilder)argument("target", EntityArgumentType.entity()))
                                .then(
                                        literal("set")
                                                .then(
                                                        literal("state")
                                                                .then (
                                                                        literal("SLEEP")
                                                                                .executes(context -> DragonManager.setStateSleep(context, (ServerCommandSource)context.getSource(), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                )
                                                                .then (
                                                                        literal("NEST")
                                                                                .executes(context -> DragonManager.setStateNest(context, (ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                )
                                                )
                                )
                                .then(
                                        literal("get")
                                                .then(
                                                        literal("state")
                                                                .executes(context -> DragonManager.getState(context, (ServerCommandSource)context.getSource(), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                )
                                )
                );
    }


    public static int setStateSleep(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {
        return setStateGeneric(context, source, entity, DragonEntity.DragonState.SLEEP);
    }
    public static int setStateNest(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {
        return setStateGeneric(context, source, entity, DragonEntity.DragonState.NEST);
    }
    public static int setStateGeneric(CommandContext context, ServerCommandSource source, Entity entity, DragonEntity.DragonState state) throws CommandSyntaxException {
        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        dragon.setState(state);
        source.sendFeedback(
                Text.translatable(
                        "command.dragonheart.dragonmanager.set.state",
                        entity.getDisplayName(),
                        Text.translatable(
                                "command.dragonheart.dragonmanager.state." + state
                        )
                ),
                true
        );
        return 0;
    }


    public static int getState(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {
        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity             dragon = (DragonEntity)entity;
        DragonEntity.DragonState state  = DragonEntity.DragonState.fromInt(entity.getDataTracker().get(DragonEntity.STATE));
        source.sendFeedback(
                Text.translatable(
                        "command.dragonheart.dragonmanager.get.state",
                        entity.getDisplayName(),
                        Text.translatable(
                                "command.dragonheart.dragonmanager.state." + state
                        )
                ),
                true
        );
        return 0;
    }


}
