package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.entity.DragonEntity;


public class DragonManagerSetState {


    public static int sleep(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {

        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        dragon.setState(DragonEntity.DragonState.SLEEP);
        source.sendFeedback(Text.translatable("command.dragonheart.dragonmanager.set.state", entity.getDisplayName(), "sleep"), true);
        return 0;

    }


    public static int stand(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {

        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        dragon.setState(DragonEntity.DragonState.STAND);
        source.sendFeedback(Text.translatable("command.dragonheart.dragonmanager.set.state", entity.getDisplayName(), "stand"), true);
        return 0;

    }


    public static int walk(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {

        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        dragon.setState(DragonEntity.DragonState.WALK);
        source.sendFeedback(Text.translatable("command.dragonheart.dragonmanager.set.state", entity.getDisplayName(), "walk"), true);
        return 0;

    }


    public static int fly(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {

        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        dragon.setState(DragonEntity.DragonState.FLY);
        source.sendFeedback(Text.translatable("command.dragonheart.dragonmanager.set.state", entity.getDisplayName(), "fly"), true);
        return 0;

    }


    public static int dive(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {

        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        dragon.setState(DragonEntity.DragonState.DIVE);
        source.sendFeedback(Text.translatable("command.dragonheart.dragonmanager.set.state", entity.getDisplayName(), "dive"), true);
        return 0;

    }


}
