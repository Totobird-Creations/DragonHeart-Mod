package net.totobirdcreations.dragonheart.block.entity.dragon.forge_core;

import net.minecraft.screen.PropertyDelegate;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;

import javax.annotation.Nullable;


public record DragonForgeCoreBlockEntityProperties(
        DragonForgeCoreBlockEntity owner
) implements PropertyDelegate {

    public static final int PROGRESS     = 0;
    public static final int MAX_PROGRESS = 1;
    public static final int COLOUR       = 2;
    public static final int SIZE         = 3;


    @Override
    public int get(int property) {
        if (owner == null) {return 0;}
        return switch (property) {
            case    PROGRESS     -> owner.progress;
            case    MAX_PROGRESS -> owner.maxProgress;
            case    COLOUR       -> DragonResourceLoader.getResource(owner.dragon).colourGlow().asInt();
            default              -> 0;
        };
    }

    @Override
    public void set(int property, int value) {
        if (owner == null) {return;}
        switch (property) {
            case PROGRESS     -> owner.progress    = value;
            case MAX_PROGRESS -> owner.maxProgress = value;
        }
    }

    @Override
    public int size() {
        return SIZE;
    }

}
