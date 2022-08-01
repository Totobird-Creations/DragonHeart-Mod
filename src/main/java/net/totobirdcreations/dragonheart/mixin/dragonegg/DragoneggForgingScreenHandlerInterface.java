package net.totobirdcreations.dragonheart.mixin.dragonegg;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ForgingScreenHandler.class)
public interface DragoneggForgingScreenHandlerInterface {

    @Accessor("input")
    Inventory getInput();

}
