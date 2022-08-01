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
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static net.minecraft.server.command.CommandManager.argument;


public class DragonManager {
    // TODO : Do something about this mess of warnings.


    public static LiteralArgumentBuilder<ServerCommandSource> getBrigadier() {
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
                                                                                .executes(context -> DragonManager.setStateSleep((ServerCommandSource)context.getSource(), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                )
                                                                .then (
                                                                        literal("NEST")
                                                                                .executes(context -> DragonManager.setStateNest((ServerCommandSource)(context.getSource()), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                                )
                                                )
                                )
                                .then(
                                        literal("get")
                                                .then(
                                                        literal("state")
                                                                .executes(context -> DragonManager.getState((ServerCommandSource)context.getSource(), EntityArgumentType.getEntity((CommandContext)context, "target")))
                                                )
                                )
                );
    }


    public static int setStateSleep(ServerCommandSource source, Entity entity) throws CommandSyntaxException {
        return setStateGeneric(source, entity, DragonEntity.DragonState.SLEEP);
    }
    public static int setStateNest(ServerCommandSource source, Entity entity) throws CommandSyntaxException {
        return setStateGeneric(source, entity, DragonEntity.DragonState.NEST);
    }
    public static int setStateGeneric(ServerCommandSource source, Entity entity, DragonEntity.DragonState state) throws CommandSyntaxException {
        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command." + DragonHeart.ID + ".dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        ((DragonEntity)entity).setState(state);
        source.sendFeedback(
                Text.translatable(
                        "command." + DragonHeart.ID + ".dragonmanager.set.state",
                        entity.getDisplayName(),
                        Text.translatable(
                                "command." + DragonHeart.ID + ".dragonmanager.state." + state
                        )
                ),
                true
        );
        return 0;
    }


    public static int getState(ServerCommandSource source, Entity entity) throws CommandSyntaxException {
        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command." + DragonHeart.ID + ".dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity.DragonState state  = DragonEntity.DragonState.fromInt(entity.getDataTracker().get(DragonEntity.STATE));
        source.sendFeedback(
                Text.translatable(
                        "command." + DragonHeart.ID + ".dragonmanager.get.state",
                        entity.getDisplayName(),
                        Text.translatable(
                                "command." + DragonHeart.ID + ".dragonmanager.state." + state
                        )
                ),
                true
        );
        return 0;
    }


}
