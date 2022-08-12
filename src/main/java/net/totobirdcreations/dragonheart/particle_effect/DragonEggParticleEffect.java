package net.totobirdcreations.dragonheart.particle_effect;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3f;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;


public class DragonEggParticleEffect extends AbstractDustParticleEffect {

    public static final Codec<DragonEggParticleEffect> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Vec3f.CODEC.fieldOf("colour").forGetter((effect) ->
                    effect.color
            )).apply(instance, DragonEggParticleEffect::new)
    );
    public static final net.minecraft.particle.ParticleEffect.Factory<DragonEggParticleEffect> PARAMETERS_FACTORY = new Factory();


    public DragonEggParticleEffect(RGBColour colour) {
        this(new Vec3f(colour.r, colour.g, colour.b));
    }
    public DragonEggParticleEffect(Vec3f colour) {
        super(colour, 1.0f);
    }


    public ParticleType<DragonEggParticleEffect> getType() {
        return ParticleEffects.DRAGON_EGG;
    }


    public static class Factory implements net.minecraft.particle.ParticleEffect.Factory<DragonEggParticleEffect> {

        @Override
        public DragonEggParticleEffect read(ParticleType<DragonEggParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            Vec3f colour = AbstractDustParticleEffect.readColor(reader);
            return new DragonEggParticleEffect(colour);
        }

        @Override
        public DragonEggParticleEffect read(ParticleType<DragonEggParticleEffect> type, PacketByteBuf buf) {
            return new DragonEggParticleEffect(AbstractDustParticleEffect.readColor(buf));
        }

    }

}
