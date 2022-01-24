package net.totobirdcreations.dragonheart.block;


import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;

public class ModBlockTags {


    public static final Tag<Block> DRAGON_UNGRIEFABLE  = TagFactory.BLOCK.create(new Identifier(DragonHeart.MOD_ID, "dragon_ungriefable"));
    public static final Tag<Block> DRAGON_EVAPORATABLE = TagFactory.BLOCK.create(new Identifier(DragonHeart.MOD_ID, "dragon_evaporatable"));
    public static final Tag<Block> DRAGON_ICEABLE       = TagFactory.BLOCK.create(new Identifier(DragonHeart.MOD_ID, "dragon_iceable"));


    public static void register() {



    }


}
