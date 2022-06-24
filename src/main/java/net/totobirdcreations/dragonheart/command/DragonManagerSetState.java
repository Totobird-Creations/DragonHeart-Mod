package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.entity.DragonEntity;


public class DragonManagerSetState {


    public static int nest(CommandContext context, ServerCommandSource source, Entity entity) throws CommandSyntaxException {

        if (! (entity instanceof DragonEntity)) {
            throw new SimpleCommandExceptionType(Text.translatable("command.dragonheart.dragonmanager.target.not_dragon", entity.getDisplayName())).create();
        }
        DragonEntity dragon = (DragonEntity)entity;
        DragonEntity.DragonState state = DragonEntity.DragonState.NEST;
        dragon.setState(state);
        source.sendFeedback(Text.translatable("command.dragonheart.dragonmanager.set.state", entity.getDisplayName(), state.toString()), true);
        return 0;

    }


}
