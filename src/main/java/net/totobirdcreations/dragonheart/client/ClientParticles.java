package net.totobirdcreations.dragonheart.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.particle.DragonFlameParticle;
import net.totobirdcreations.dragonheart.particle.Particles;


public class ClientParticles {

    public static void register() {
        DragonHeart.LOGGER.debug("Registering client particles.");

        ParticleFactoryRegistry.getInstance().register(Particles.DRAGON_FLAME, DragonFlameParticle.Factory::new);
    }

}
