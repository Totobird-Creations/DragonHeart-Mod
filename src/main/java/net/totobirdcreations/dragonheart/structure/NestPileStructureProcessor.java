package net.totobirdcreations.dragonheart.structure;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class NestPileStructureProcessor extends StructureProcessor {

    public static final NestPileStructureProcessor        INSTANCE = new NestPileStructureProcessor();
    public static final Codec<NestPileStructureProcessor> CODEC    = Codec.unit(() -> INSTANCE);

    public static final HashMap<Block, Float> CHANCES = new HashMap<>();
    static {
        CHANCES.put(Blocks. WHITE_WOOL            , 1.0f  );
        CHANCES.put(Blocks. ORANGE_WOOL           , 0.75f );
        CHANCES.put(Blocks. MAGENTA_WOOL          , 0.5f  );
        CHANCES.put(Blocks. LIGHT_BLUE_WOOL       , 0.25f );
    }
    public static final PileGroups GROUPS = new PileGroups().add(
            Blocks.COPPER_ORE.getDefaultState(),
            Blocks.DEEPSLATE_COPPER_ORE.getDefaultState(),
            Blocks.DEEPSLATE_COPPER_ORE.getDefaultState(),
            Blocks.RAW_COPPER_BLOCK.getDefaultState(),
            Blocks.COPPER_BLOCK.getDefaultState()
    ).add(
            Blocks.IRON_ORE.getDefaultState(),
            Blocks.DEEPSLATE_IRON_ORE.getDefaultState(),
            Blocks.DEEPSLATE_IRON_ORE.getDefaultState(),
            Blocks.RAW_IRON_BLOCK.getDefaultState(),
            Blocks.IRON_BLOCK.getDefaultState()
    ).add(
            Blocks.GOLD_ORE.getDefaultState(),
            Blocks.DEEPSLATE_GOLD_ORE.getDefaultState(),
            Blocks.DEEPSLATE_GOLD_ORE.getDefaultState(),
            Blocks.RAW_GOLD_BLOCK.getDefaultState(),
            Blocks.GOLD_BLOCK.getDefaultState()
    ).add(
            Blocks.EMERALD_ORE.getDefaultState(),
            Blocks.EMERALD_ORE.getDefaultState(),
            Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState(),
            Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState(),
            Blocks.EMERALD_BLOCK.getDefaultState()
    ).add(
            Blocks.DIAMOND_ORE.getDefaultState(),
            Blocks.DIAMOND_ORE.getDefaultState(),
            Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState(),
            Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState(),
            Blocks.DIAMOND_BLOCK.getDefaultState()
    ).add(
            Blocks.AMETHYST_BLOCK.getDefaultState(),
            Blocks.CRYING_OBSIDIAN.getDefaultState(),
            Blocks.OBSIDIAN.getDefaultState(),
            Blocks.OBSIDIAN.getDefaultState()
    ).add(
            Blocks.SCULK.getDefaultState(),
            Blocks.BONE_BLOCK.getDefaultState(),
            Blocks.BONE_BLOCK.getDefaultState()
    ).add(
            Blocks.MUSHROOM_STEM.getDefaultState(),
            Blocks.SHROOMLIGHT.getDefaultState(),
            Blocks.HONEYCOMB_BLOCK.getDefaultState(),
            Blocks.HONEYCOMB_BLOCK.getDefaultState(),
            Blocks.HONEY_BLOCK.getDefaultState()
    );

    @Override
    public StructureTemplate.StructureBlockInfo process(
            WorldView world, BlockPos pos, BlockPos pivot,
            StructureTemplate.StructureBlockInfo originalInfo,
            StructureTemplate.StructureBlockInfo currentInfo,
            StructurePlacementData data
    ) {
        if (DataHelper.chooseDragonTypeForPos(world, pos, data.getRandom(pos)) == null) {
            return originalInfo;
        }

        Random random    = data.getRandom(currentInfo.pos);
        Float  threshold = CHANCES.get(currentInfo.state.getBlock());
        if (threshold != null) {
            // Block is wool with chance attached.
            if (random.nextFloat() <= threshold) {
                // If chance is matched, place treasure block.
                random                      = data.getRandom(pos);
                ArrayList<BlockState> group = GROUPS.groups.get(random.nextBetweenExclusive(0, GROUPS.groups.size()));
                random                      = data.getRandom(currentInfo.pos);
                BlockState            state = group.get(random.nextBetweenExclusive(0, group.size()));
                currentInfo = new StructureTemplate.StructureBlockInfo(
                        currentInfo.pos,
                        state,
                        new NbtCompound()
                );
            } else {
                currentInfo = new StructureTemplate.StructureBlockInfo(
                        currentInfo.pos,
                        Blocks.AIR.getDefaultState(),
                        new NbtCompound()
                );
            }
        }
        // Underwater check
        if (currentInfo.state.isOf(Blocks.AIR) && originalInfo.state.isOf(Blocks.WATER)) {
            // Handle underwater spawning.
            currentInfo = new StructureTemplate.StructureBlockInfo(
                    currentInfo.pos,
                    Blocks.WATER.getDefaultState(),
                    new NbtCompound()
            );
        }
        // Return
        return currentInfo;
    }


    @Override
    public StructureProcessorType<?> getType() {
        return Structures.NEST_BASE_PROCESSOR_TYPE;
    }


    public record PileGroups(ArrayList<ArrayList<BlockState>> groups) {

        public PileGroups() {
            this(new ArrayList<>());
        }

        public PileGroups add(BlockState... group) {
            this.groups.add(new ArrayList<>(List.of(group)));
            return this;
        }

    }

}
