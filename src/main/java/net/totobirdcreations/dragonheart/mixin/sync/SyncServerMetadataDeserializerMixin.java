package net.totobirdcreations.dragonheart.mixin.sync;

import com.google.gson.*;
import net.minecraft.server.ServerMetadata;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import net.totobirdcreations.dragonheart.util.mixin.sync.SyncServerMetadataMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;


@Mixin(ServerMetadata.Deserializer.class)
public abstract class SyncServerMetadataDeserializerMixin implements JsonDeserializer<ServerMetadata>, JsonSerializer<ServerMetadata> {

    @Inject(
            method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/server/ServerMetadata;",
            at = @At("RETURN")
    )
    public void deserializeInjectReturn(JsonElement element, Type type, JsonDeserializationContext context, CallbackInfoReturnable<ServerMetadata> callback) {
        ServerMetadata                   metadata  = callback.getReturnValue();
        JsonObject                       json      = JsonHelper.asObject(element, "status");
        SyncServerMetadataMixinInterface imetadata = (SyncServerMetadataMixinInterface) metadata;
        if (json.has("realDescription")) {
            imetadata.setRealDescription(context.deserialize(json.get("realDescription"), Text.class));
        } else {
            imetadata.setRealDescription(metadata.getDescription());
        }
    }


    @Inject(
            method = "serialize(Lnet/minecraft/server/ServerMetadata;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;",
            at = @At("RETURN")
    )
    public void serializeInjectReturn(ServerMetadata metadata, Type type, JsonSerializationContext context, CallbackInfoReturnable<JsonElement> callback) {
        JsonObject                       json      = (JsonObject)(callback.getReturnValue());
        SyncServerMetadataMixinInterface imetadata = (SyncServerMetadataMixinInterface) metadata;

        if (imetadata.getRealDescription() != null) {
            json.add("realDescription", context.serialize(imetadata.getRealDescription()));
        }

    }


    @Redirect(
            method = "serialize(Lnet/minecraft/server/ServerMetadata;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/ServerMetadata;getDescription()Lnet/minecraft/text/Text;"
            )
    )
    public Text serializeRedirectGetDescription(ServerMetadata metadata) {
        return ((SyncServerMetadataMixinInterface) metadata).getModifiedDescription();
    }

}
