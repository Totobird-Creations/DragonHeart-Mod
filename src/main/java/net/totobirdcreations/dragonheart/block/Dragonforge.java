package net.totobirdcreations.dragonheart.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;



public class Dragonforge extends Block {


    public Dragonforge(Settings settings) {

        super(settings);

    }


    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {

        return PistonBehavior.BLOCK;

    }


}
