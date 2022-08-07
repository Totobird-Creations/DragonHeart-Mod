package net.totobirdcreations.dragonheart.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonGriefedBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public class Commands {

    public static final SuggestionProvider<ServerCommandSource> DRAGON_TYPES = SuggestionProviders.register(new Identifier("dragon_types"), (context, builder) ->
            CommandSource.suggestIdentifiers(DragonResourceLoader.getIdentifiers(), builder)
    );


    public static void registerDebug() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            CommandNode<ServerCommandSource> root = CommandManager
                    .literal(DragonHeart.ID + "debug")
                    .build();

            CommandNode<ServerCommandSource> grief = CommandManager
                    .literal("grief")
                    .build();

            CommandNode<ServerCommandSource> griefType = CommandManager
                    .argument("griefType", IdentifierArgumentType.identifier())
                    .suggests(DRAGON_TYPES)
                    .executes(Commands::grief)
                    .build();

            CommandNode<ServerCommandSource> griefPosition = CommandManager
                    .argument("griefPosition", BlockPosArgumentType.blockPos())
                    .executes(Commands::grief)
                    .build();

            dispatcher.getRoot().addChild(root);
            root.addChild(grief);
            grief.addChild(griefType);
            griefType.addChild(griefPosition);

        });
    }


    public static int grief(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        Identifier          type   = IdentifierArgumentType.getIdentifier(context, "griefType");
        BlockPos            pos;
        try {
            pos = BlockPosArgumentType.getBlockPos(context, "griefPosition");
        } catch (IllegalArgumentException err) {
            pos = new BlockPos(source.getPosition());
        }
        DragonGriefedBlockEntity.convert(source.getWorld(), pos, type);
        return 1;
    }


    public static void register() {
        DragonHeart.LOGGER.debug("Registering commands.");
        if (DragonHeart.DEVENV) {
            registerDebug();
        }
    }


}
