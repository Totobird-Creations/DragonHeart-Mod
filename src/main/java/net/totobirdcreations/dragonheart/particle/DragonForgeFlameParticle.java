package net.totobirdcreations.dragonheart.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;


public class DragonForgeFlameParticle extends SpriteBillboardParticle {

    public DragonForgeFlameParticle(ClientWorld world, double x, double y, double z, SpriteProvider provider, double dx, double dy, double dz) {
        super(world, x, y, z, dx, dy, dz);

        this.x                  = x;
        this.y                  = y;
        this.z                  = z;
        this.scale              = 0.0f;
        this.maxAge             = (int)((random.nextFloat() * 0.875 + 0.125) * 60.0f);
        float angle   = random.nextFloat() * ((float)(Math.PI) * 2.0f);
        float hAmount = random.nextFloat() * 0.025f;
        float vAmount = random.nextFloat() * 0.1f;
        this.setVelocity(Math.cos(angle) * hAmount, vAmount, Math.sin(angle) * hAmount);
        this.setSpriteForAge(provider);

        this.red   = (float)dx;
        this.green = (float)dy;
        this.blue  = (float)dz;
        this.alpha = 1.0f;
    }


    @Override
    public void tick() {
        super.tick();
        float t = ((float)(this.age)) / ((float)(this.maxAge));
        this.scale = 0.25f - 0.25f * t;
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }


    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        public final SpriteProvider provider;

        public Factory(SpriteProvider provider) {
            this.provider = provider;
        }

        public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double dx, double dy, double dz) {
            return new DragonForgeFlameParticle(world, x, y, z, this.provider, dx, dy, dz);
        }
    }

}
