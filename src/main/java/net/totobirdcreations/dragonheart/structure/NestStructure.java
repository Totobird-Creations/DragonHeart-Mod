package net.totobirdcreations.dragonheart.structure;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;


public class NestStructure extends JigsawStructure {


    public NestStructure(
            Config config,
            RegistryEntry<StructurePool> startPool,
            Optional<Identifier> startJigsawName,
            int size,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Optional<Heightmap.Type> projectStartToHeightmap,
            int maxDistanceFromCenter
    ) {
        super(config, startPool, startJigsawName, size, startHeight, useExpansionHack, projectStartToHeightmap, maxDistanceFromCenter);
        //config.biomes = RegistryEntryList.of(RegistryEntry.of(TagResources.ALL_BIOMES));
    }


    public StructureType<?> getType() {
        return Structures.NEST_TYPE;
    }

}
