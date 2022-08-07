package net.totobirdcreations.dragonheart.particle;

import com.mojang.serialization.Codec;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;


public class Particles {

    public static ParticleType<DragonFlameParticleEffect> DRAGON_FLAME = Registry.register(
            Registry.PARTICLE_TYPE,
            new Identifier(DragonHeart.ID, "dragon_flame"),
            new ParticleType<>(false, DragonFlameParticleEffect.PARAMETERS_FACTORY) {
                public Codec<DragonFlameParticleEffect> getCodec() {
                    return DragonFlameParticleEffect.CODEC;
                }
            }
    );


    public static void createDragonForgeFlame(World world, BlockPos pos, Identifier type) {
        RGBColour colour = DragonResourceLoader.getResource(type).colourGlow();
        Vec3f origin = new Vec3f(pos.getX() + 0.5f, pos.getY() + 0.875f, pos.getZ() + 0.5f);

        Random random     = Random.create();
        float  angle      = random.nextFloat() * ((float)(Math.PI) * 2.0f);
        float  hAmount    = random.nextFloat() * 0.075f;
        float  vAmountMax = 0.5f;
        float  vAmount    = random.nextFloat() * (vAmountMax / 2.0f) + (vAmountMax / 2.0f);

        world.addParticle(
                new DragonFlameParticleEffect(colour),
                origin.getX() + Math.cos(angle) * hAmount * 2.0f,
                origin.getY(),
                origin.getZ() + Math.sin(angle) * hAmount * 2.0f,
                Math.cos(angle) * hAmount, vAmount, Math.sin(angle) * hAmount
        );
    }


    public static void register() {
        DragonHeart.LOGGER.debug("Registering particles.");
    }

}
