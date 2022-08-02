package net.totobirdcreations.dragonheart.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.totobirdcreations.dragonheart.particle.DragonForgeFlameParticle;
import net.totobirdcreations.dragonheart.particle.Particles;


public class ClientParticles {

    public static void register() {
        ParticleFactoryRegistry.getInstance().register(Particles.DRAGON_FORGE_FLAME, DragonForgeFlameParticle.Factory::new);
    }

}
