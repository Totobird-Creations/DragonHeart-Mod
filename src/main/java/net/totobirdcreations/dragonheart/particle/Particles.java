package net.totobirdcreations.dragonheart.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;


public class Particles {

    public static final DefaultParticleType DRAGON_FORGE_FLAME = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(DragonHeart.ID, "dragon_forge_flame"), DRAGON_FORGE_FLAME);
    }

}
