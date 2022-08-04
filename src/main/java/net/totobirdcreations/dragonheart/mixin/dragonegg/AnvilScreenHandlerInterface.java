package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(AnvilScreenHandler.class)
public interface AnvilScreenHandlerInterface {

    @Accessor("levelCost")
    Property getLevelCost();

    @Accessor("newItemName")
    String getNewItemName();

}
