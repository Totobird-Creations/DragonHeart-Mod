package net.totobirdcreations.dragonheart.block.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;



public class ItemBlock {


    public final Block block;
    public final Item  item;


    public ItemBlock(Block block, Item item) {
        this.block = block;
        this.item  = item;
    }


}
