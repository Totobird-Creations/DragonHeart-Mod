package net.totobirdcreations.dragonheart.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.totobirdcreations.dragonheart.DragonHeart;


public class Structures {

    public static StructureType<? extends JigsawStructure> NEST_TYPE = registerStructureType("nest", NestStructure.CODEC);

    public static <S extends Structure> StructureType<S> registerStructureType(String id, Codec<S> codec) {
        return Registry.register(
                Registry.STRUCTURE_TYPE,
                new Identifier(DragonHeart.ID, id),
                () -> codec
        );
    }


    public static StructureProcessorType<NestStructureProcessor> NEST_PROCESSOR_TYPE = registerProcessor("nest", NestStructureProcessor.CODEC);

    public static <P extends StructureProcessor> StructureProcessorType<P> registerProcessor(String id, Codec<P> codec) {
        return Registry.register(
                Registry.STRUCTURE_PROCESSOR,
                new Identifier(DragonHeart.ID, id),
                () -> codec
        );
    }


    public static void register() {
        DragonHeart.LOGGER.debug("Registering structures.");
    }

}
