package net.totobirdcreations.dragonheart.block;


import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;

public class ModBlockTags {


    public static final TagKey<Block> DRAGON_UNGRIEFABLE  = TagKey.of(Registry.BLOCK_KEY, new Identifier( DragonHeart.MOD_ID , "dragon_ungriefable"  ));
    @SuppressWarnings("unused")
    public static final TagKey<Block> DRAGON_EVAPORATABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier( DragonHeart.MOD_ID , "dragon_evaporatable" ));
    @SuppressWarnings("unused")
    public static final TagKey<Block> DRAGON_ICEABLE      = TagKey.of(Registry.BLOCK_KEY, new Identifier( DragonHeart.MOD_ID , "dragon_iceable"      ));


    public static void register() {}


}
