package net.totobirdcreations.dragonheart.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;



public class ModParticles {

    public static final ParticleType DRAGONBREATH_FIRE = registerParticle(
            "dragonbreath_fire"
    );

    public static ParticleType registerParticle(String name) {
        return Registry.register(
                Registry.PARTICLE_TYPE,
                new Identifier(DragonHeart.MOD_ID),
                FabricParticleTypes.simple()
        );
    }

    public static void register() {
        DragonHeart.LOGGER.info("Registering particles.");
    }

}
