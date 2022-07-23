package net.totobirdcreations.dragonheart.mixin.sync;

import net.minecraft.server.ServerMetadata;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ServerMetadata.class)
public interface SyncServerMetadataInterface {

    @Accessor("description")
    Text getDescription();

}
