package net.totobirdcreations.dragonheart.block.dragon.forge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;


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
