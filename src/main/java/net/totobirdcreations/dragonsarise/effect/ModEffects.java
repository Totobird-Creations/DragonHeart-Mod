package net.totobirdcreations.dragonsarise.effect;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonsarise.DragonsArise;

public class ModEffects {

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(DragonsArise.MOD_ID, "frozen"), new FrozenEffect());
    }

}
