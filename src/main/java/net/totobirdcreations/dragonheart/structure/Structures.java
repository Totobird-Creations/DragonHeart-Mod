package net.totobirdcreations.dragonheart.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;


public class Structures {

    public static StructureProcessorType<DragonNestStructureProcessor> DRAGON_NEST = registerProcessor("nest", DragonNestStructureProcessor.CODEC);


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
