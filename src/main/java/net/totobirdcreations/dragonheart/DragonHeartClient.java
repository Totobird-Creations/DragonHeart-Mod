package net.totobirdcreations.dragonheart;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.*;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonFireEntityRenderer;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonIceEntityRenderer;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonLightningEntityRenderer;
import net.totobirdcreations.dragonheart.item.MiscItems;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.screen.ModScreens;
import net.totobirdcreations.dragonheart.util.effect.DeafenedEffectLivingEntityInterface;
import net.totobirdcreations.dragonheart.util.ModRegistries;


@Environment(EnvType.CLIENT)
public class DragonHeartClient implements ClientModInitializer, ShaderEffectRenderCallback {


    public static final ManagedShaderEffect DEAFENED_SHADER        = registerShader("deafened");
    public              Uniform1f           UNIFORM_SATURATION     = DEAFENED_SHADER.findUniform1f("Saturation");
    public              Uniform1f           UNIFORM_BLURRADIUS     = DEAFENED_SHADER.findUniform1f("Radius");
    public              Uniform1f           UNIFORM_CONTRAST       = DEAFENED_SHADER.findUniform1f("Contrast");
    public              Uniform3f           UNIFORM_MODULATE       = DEAFENED_SHADER.findUniform3f("Modulate");
    public              float               deafenedShaderAmount  = 0;


    public static ManagedShaderEffect registerShader(String name) {
        return ShaderEffectManager.getInstance()
                .manage(new Identifier(DragonHeart.MOD_ID, "shaders/post/" + name + ".json"));
    }



    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonscale)stack.getItem()).getColor(stack), MiscItems.DRAGONSCALE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_ICE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_FIRE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_LIGHTNING);

        EntityRendererRegistry.register(ModEntities.DRAGON_FIRE      , DragonFireEntityRenderer::new      );
        EntityRendererRegistry.register(ModEntities.DRAGON_ICE       , DragonIceEntityRenderer::new       );
        EntityRendererRegistry.register(ModEntities.DRAGON_LIGHTNING , DragonLightningEntityRenderer::new );

        ShaderEffectRenderCallback.EVENT.register(this);

        ModScreens.register();
        ModRegistries.register();

    }


    public int getDragonBucketItemColour(ItemStack stack, int tintIndex) {
        Dragonbucket bucket = (Dragonbucket)stack.getItem();
        return tintIndex == 1 ? bucket.getColor(stack) : 16777215;
    }


    @Override
    public void renderShaderEffects(float tickDelta) {
        ClientPlayerEntity player     = MinecraftClient.getInstance().player;
        boolean            isDeafened = ((DeafenedEffectLivingEntityInterface)player).isDeafened();
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
