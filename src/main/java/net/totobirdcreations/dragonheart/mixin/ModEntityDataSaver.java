package net.totobirdcreations.dragonheart.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.totobirdcreations.dragonheart.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(Entity.class)
public abstract class ModEntityDataSaver implements IEntityDataSaver {


    public NbtCompound persistentData;


    @Override
    public NbtCompound getPersistentData() {

        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;

    }


    @Inject(method = "writeNbt", at = @At("HEAD"))
    public void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {

        if (persistentData != null) {
            nbt.put("dragons_arise_data", persistentData);

        }
    }


    @Inject(method = "readNbt", at = @At("HEAD"))
    public void injectReadMethod(NbtCompound nbt, CallbackInfo info) {

        if (nbt.contains("dragons_arise_data", 10)) {
            persistentData = nbt.getCompound("dragons_arise_data");
        }

    }


}
