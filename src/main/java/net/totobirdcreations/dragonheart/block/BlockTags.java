package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.totobirdcreations.dragonheart.DragonHeart;

import java.util.Optional;


public class BlockTags {

    public static final TagKey<Block> BREATH_IMMUNE = TagKey.of(Registry.BLOCK_KEY, new Identifier(DragonHeart.ID, "breath_immune" ));
    public static final TagKey<Block> ROAR_IMMUNE   = TagKey.of(Registry.BLOCK_KEY, new Identifier(DragonHeart.ID, "roar_immune"   ));


    public static void register() {}


    public static boolean isOf(BlockState state, TagKey<Block> tag) {
        Optional<RegistryKey<Block>> key = Registry.BLOCK.getKey(state.getBlock());
        return key.isPresent() && Registry.BLOCK.getOrCreateEntry(key.get()).isIn(tag);
    }

}
