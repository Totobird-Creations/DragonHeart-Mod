package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;


public abstract class DragonForgeBlock extends DragonBlock {


    public DragonForgeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(Properties.POWERED, false)
        );
    }


    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.POWERED);
    }


    @Override
    public int getLightLevel(BlockState state) {
        // If powered, cast light on the world.
        return state.get(Properties.POWERED)
                ? 7
                : 0;
    }


}
