package net.totobirdcreations.dragonheart.util.mixin.sync;


import net.minecraft.text.Text;

public interface SyncServerMetadataMixinInterface {

    Text getRealDescription();

    void setRealDescription(Text value);

    Text getModifiedDescription();

}
