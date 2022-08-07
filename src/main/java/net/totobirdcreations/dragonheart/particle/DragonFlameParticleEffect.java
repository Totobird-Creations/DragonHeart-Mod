package net.totobirdcreations.dragonheart.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.*;
import net.minecraft.util.math.Vec3f;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;


public class DragonFlameParticleEffect extends AbstractDustParticleEffect {

    public static final Codec<DragonFlameParticleEffect> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(Vec3f.CODEC.fieldOf("colour").forGetter((effect) ->
                    effect.color
            )).apply(instance, DragonFlameParticleEffect::new)
    );
    public static final ParticleEffect.Factory<DragonFlameParticleEffect> PARAMETERS_FACTORY = new Factory();


    public DragonFlameParticleEffect(RGBColour colour) {
        this(new Vec3f(colour.r, colour.g, colour.b));
    }
    public DragonFlameParticleEffect(Vec3f colour) {
        super(colour, 1.0f);
    }


    public ParticleType<DragonFlameParticleEffect> getType() {
        return Particles.DRAGON_FLAME;
    }


    public static class Factory implements ParticleEffect.Factory<DragonFlameParticleEffect> {

        @Override
        public DragonFlameParticleEffect read(ParticleType<DragonFlameParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            Vec3f colour = AbstractDustParticleEffect.readColor(reader);
            return new DragonFlameParticleEffect(colour);
        }

        @Override
        public DragonFlameParticleEffect read(ParticleType<DragonFlameParticleEffect> type, PacketByteBuf buf) {
            return new DragonFlameParticleEffect(AbstractDustParticleEffect.readColor(buf));
        }

    }

}
