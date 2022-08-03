package net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator;

import net.minecraft.screen.PropertyDelegate;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;


public record DragoneggIncubatorBlockEntityProperties(
        DragoneggIncubatorBlockEntity owner
) implements PropertyDelegate {

    public static final int POWER     = 0;
    public static final int MAX_POWER = 1;
    public static final int COLOUR    = 2;
    public static final int SIZE      = 3;


    @Override
    public int get(int property) {
        if (owner == null) {return 0;}
        return switch (property) {
            case    POWER     -> owner.power;
            case    MAX_POWER -> owner.maxPower;
            case    COLOUR    -> DragonResourceLoader.getResource(owner.dragon).colourGlow().asInt();
            default              -> 0;
        };
    }

    @Override
    public void set(int property, int value) {
        if (owner == null) {return;}
        switch (property) {
            case POWER     -> owner.power    = value;
            case MAX_POWER -> owner.maxPower = value;
        }
    }

    @Override
    public int size() {
        return SIZE;
    }

}
