package net.totobirdcreations.dragonheart.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.totobirdcreations.dragonheart.particle_effect.DragonEggParticleEffect;


public class DragonEggParticle extends AbstractDustParticle<DragonEggParticleEffect> {

    public final Sprite sprite;


    public DragonEggParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, DragonEggParticleEffect parameters, SpriteProvider spriteProvider) {
        super(world, x, y, z, vx, vy, vz, parameters, spriteProvider);
        this.maxAge            = (int)((random.nextFloat() * 2.5 + 2.5) * 60.0f);
        this.velocityX         = vx;
        this.velocityY         = vy;
        this.velocityZ         = vz;
        this.collidesWithWorld = true;
        this.sprite            = spriteProvider.getSprite(this.random);
    }


    @Override
    public float darken(float colour, float multiplier) {
        return colour;
    }


    @Override
    public void tick() {
        super.tick();
        this.velocityY -= 0.05f;
        this.setSprite(sprite);
        if (this.onGround) {
            this.markDead();
        }
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DragonEggParticleEffect> {
        private final SpriteProvider provider;

        public Factory(SpriteProvider provider) {
            this.provider = provider;
        }

        public Particle createParticle(DragonEggParticleEffect effect, ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
            return new DragonEggParticle(world, x, y, z, vx, vy, vz, effect, this.provider);
        }
    }

}
