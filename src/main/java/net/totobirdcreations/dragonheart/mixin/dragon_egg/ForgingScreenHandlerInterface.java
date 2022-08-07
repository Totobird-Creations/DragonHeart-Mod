package net.totobirdcreations.dragonheart.mixin.dragon_egg;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ForgingScreenHandler.class)
public interface ForgingScreenHandlerInterface {

    @Accessor("input")
    Inventory getInput();

}
