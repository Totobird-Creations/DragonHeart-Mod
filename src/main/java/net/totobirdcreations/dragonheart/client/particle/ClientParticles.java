package net.totobirdcreations.dragonheart.client.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.particle_effect.ParticleEffects;


public class ClientParticles {

    public static void register() {
        DragonHeart.LOGGER.debug("Registering client particles.");

        ParticleFactoryRegistry.getInstance().register(ParticleEffects. DRAGON_FLAME , DragonFlameParticle .Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleEffects. DRAGON_EGG   , DragonEggParticle   .Factory::new);
    }

}
