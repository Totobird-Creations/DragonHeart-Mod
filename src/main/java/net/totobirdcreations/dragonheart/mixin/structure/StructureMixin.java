package net.totobirdcreations.dragonheart.mixin.structure;

import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(Structure.class)
public class StructureMixin {

    /*public Structure structure;


    @Inject(
            method = "createStructureStart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/structure/Structure;isBiomeValid(Lnet/minecraft/world/gen/structure/Structure$StructurePosition;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/gen/noise/NoiseConfig;Ljava/util/function/Predicate;)Z"
            )
    )
    public void createStructureStart(
            DynamicRegistryManager dynamicRegistryManager,
            ChunkGenerator chunkGenerator, BiomeSource biomeSource, NoiseConfig noiseConfig,
            StructureTemplateManager structureTemplateManager,
            long seed, ChunkPos chunkPos, int references,
            HeightLimitView world, Predicate<RegistryEntry<Biome>> validBiomes,
            CallbackInfoReturnable<StructureStart> callback
    ) {
        structure = (Structure)(Object) this;
    }


    @Redirect(
            method = "createStructureStart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/structure/Structure;isBiomeValid(Lnet/minecraft/world/gen/structure/Structure$StructurePosition;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/gen/noise/NoiseConfig;Ljava/util/function/Predicate;)Z"
            )
    )
    public boolean isBiomeValid(Structure.StructurePosition result, ChunkGenerator chunkGenerator, NoiseConfig noiseConfig, Predicate<RegistryEntry<Biome>> validBiomes) {
        if (structure instanceof NestStructure) {
            for (Identifier id : DragonResourceLoader.getIdentifiers()) {
                DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);
                BlockPos pos = result.position();
                if (chunkGenerator.getBiomeSource().getBiome(
                            BiomeCoords.fromBlock(pos.getX()),
                            BiomeCoords.fromBlock(pos.getY()),
                            BiomeCoords.fromBlock(pos.getZ()),
                            noiseConfig.getMultiNoiseSampler()
                    ).isIn(resource.spawnsIn())
                ) {
                    return true;
                }
            }
            return false;
        } else {
            return Structure.isBiomeValid(result, chunkGenerator, noiseConfig, validBiomes);
        }
    }*/

}
