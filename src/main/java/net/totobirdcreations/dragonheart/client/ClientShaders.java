package net.totobirdcreations.dragonheart.client;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.util.mixin.deafenedeffect.DeafenedEffectLivingEntityMixinInterface;


@Environment(EnvType.CLIENT)
public class ClientShaders implements ShaderEffectRenderCallback {


    public static ManagedShaderEffect registerShader(String name) {
        return ShaderEffectManager.getInstance()
                .manage(new Identifier(DragonHeart.ID, "shaders/post/" + name + ".json"));
    }


    public static ManagedShaderEffect DEAFENED_SHADER = registerShader("deafened");

    public static Uniform1f UNIFORM_SATURATION = DEAFENED_SHADER.findUniform1f("Saturation");
    public static Uniform1f UNIFORM_BLURRADIUS = DEAFENED_SHADER.findUniform1f("Radius");
    public static Uniform1f UNIFORM_CONTRAST   = DEAFENED_SHADER.findUniform1f("Contrast");
    public static Uniform3f UNIFORM_MODULATE   = DEAFENED_SHADER.findUniform3f("Modulate");

    public static float deafenedShaderAmount = 0;


    public static void register() {
        DragonHeart.LOGGER.debug("Registering client shaders.");

        ShaderEffectRenderCallback.EVENT.register(new ClientShaders());
    }


    @Override
    public void renderShaderEffects(float tickDelta) {
        ClientPlayerEntity player     = MinecraftClient.getInstance().player;
        assert player != null;
        boolean            isDeafened = ((DeafenedEffectLivingEntityMixinInterface)player).isDeafened();
        deafenedShaderAmount          = Math.min(Math.max(deafenedShaderAmount + (isDeafened ? tickDelta * 0.1f : -tickDelta * 0.02f), 0.0f), 1.0f);
        if (deafenedShaderAmount > 0.0) {
            UNIFORM_SATURATION .set( 1.0f - 0.75f * deafenedShaderAmount              );
            UNIFORM_BLURRADIUS .set( 5.0f * deafenedShaderAmount                      );
            UNIFORM_CONTRAST   .set( deafenedShaderAmount                             );
            UNIFORM_MODULATE   .set( 1.0f, 1.0f, 1.0f - 0.125f * deafenedShaderAmount );
            DEAFENED_SHADER    .render(tickDelta);
        }
    }

}
