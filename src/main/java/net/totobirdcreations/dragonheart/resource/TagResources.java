package net.totobirdcreations.dragonheart.resource;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.totobirdcreations.dragonheart.DragonHeart;

import java.util.Optional;

public class TagResources {

    // Blocks
    public static final TagKey<Block> BREATH_IMMUNE = TagKey.of(Registry.BLOCK_KEY, new Identifier(DragonHeart.ID, "breath_immune" ));
    public static final TagKey<Block> ROAR_IMMUNE   = TagKey.of(Registry.BLOCK_KEY, new Identifier(DragonHeart.ID, "roar_immune"   ));
    public static boolean isOf(BlockState state, TagKey<Block> tag) {
        Optional<RegistryKey<Block>> key = Registry.BLOCK.getKey(state.getBlock());
        return key.isPresent() && Registry.BLOCK.getOrCreateEntry(key.get()).isIn(tag);
    }

    // Biomes
    public static final TagKey<Biome> ALL_BIOMES = TagKey.of(Registry.BIOME_KEY, new Identifier(DragonHeart.ID, "all"));


    public static void register() {
    }

}
