package net.totobirdcreations.dragonheart.block.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;


public record BlockAndItem(
        Block block,
        Item  item
) { }
