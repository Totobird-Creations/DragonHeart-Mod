package net.totobirdcreations.dragonheart.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;


public class DragonFlameParticle extends AbstractDustParticle<DragonFlameParticleEffect> {

    public DragonFlameParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, DragonFlameParticleEffect parameters, SpriteProvider spriteProvider) {
        super(world, x, y, z, vx, vy, vz, parameters, spriteProvider);
        this.maxAge = (int)((random.nextFloat() * 0.125 + 0.125) * 60.0f);
        this.velocityX = vx;
        this.velocityY = vy;
        this.velocityZ = vz;
    }


    @Override
    public float darken(float colour, float multiplier) {
        return colour;
    }


    @Override
    public void tick() {
        super.tick();
        float t = ((float)(this.age)) / ((float)(this.maxAge));
        this.scale = (1.0f - t) * 0.5f;
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }


    public int getBrightness(float tint) {
        return 15728880;
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DragonFlameParticleEffect> {
        private final SpriteProvider provider;

        public Factory(SpriteProvider provider) {
            this.provider = provider;
        }

        public Particle createParticle(DragonFlameParticleEffect effect, ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
            return new DragonFlameParticle(world, x, y, z, vx, vy, vz, effect, this.provider);
        }
    }

}
