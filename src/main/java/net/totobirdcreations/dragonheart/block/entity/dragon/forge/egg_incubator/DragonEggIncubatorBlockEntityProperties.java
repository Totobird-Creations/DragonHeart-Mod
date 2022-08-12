package net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator;

import net.minecraft.screen.PropertyDelegate;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public record DragonEggIncubatorBlockEntityProperties(
        DragonEggIncubatorBlockEntity owner
) implements PropertyDelegate {

    public static final int POWER     = 0;
    public static final int MAX_POWER = 1;
    public static final int COLOUR    = 2;
    public static final int SIZE      = 3;


    @Override
    public int get(int property) {
        if (owner == null) {return 0;}
        return switch (property) {
            case    POWER     -> owner.time;
            case    MAX_POWER -> owner.maxTime;
            case    COLOUR    -> DragonResourceLoader.getResource(owner.power).colourGlow().asInt();
            default              -> 0;
        };
    }

    @Override
    public void set(int property, int value) {
        if (owner == null) {return;}
        switch (property) {
            case POWER     -> owner.time = value;
            case MAX_POWER -> owner.maxTime = value;
        }
    }

    @Override
    public int size() {
        return SIZE;
    }

}
