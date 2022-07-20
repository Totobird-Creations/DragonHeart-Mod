package net.totobirdcreations.dragonheart.util.mixin.dragonegg;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;

import javax.annotation.Nullable;


public interface DragoneggTextFieldWidgetMixinInterface {

    void setAnvilScreen(@Nullable AnvilScreen screen);

}
