package net.totobirdcreations.dragonheart.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@Config(name = "dragonheart")
@Config.Gui.Background("dragonheart:textures/gui/configuration/background.png")
public class ConfigData implements me.shedaniel.autoconfig.ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public Dragon dragon = new Dragon();
    public static class Dragon {

        @ConfigEntry.Gui.CollapsibleObject
        public Wakeup wakeup = new Wakeup();
        public static class Wakeup {

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
            public int   vibrations     = 5;

            @ConfigEntry.Gui.Tooltip
            public float roar_radius    = 16.0f;

            @ConfigEntry.Gui.Tooltip
            public float roar_knockback = 100.0f;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 0, max = 25)
            public int   roar_destroy   = 25;

            @ConfigEntry.Gui.Tooltip
            public float roar_damage    = 10.0f;

        }

        @ConfigEntry.Gui.CollapsibleObject
        public Age age = new Age();
        public static class Age {

            @ConfigEntry.Gui.Tooltip
            public int stage_ticks     = 600000;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = -1, max = 4)
            public int min_breed_stage = 2;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = -1, max = 4)
            public int min_mount_stage = 3;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = -1, max = 4)
            public int min_xp_stage    = 2;

        }

    }

    /*
    @SuppressWarnings("unused")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.CollapsibleObject
    Debug debug = new Debug();
    static class Debug {

        @SuppressWarnings("unused")
        @ConfigEntry.Gui.Tooltip
        boolean initialization_logging = true;

    }
    */



    @Override
    public void validatePostLoad() /* throws ValidationException */ {

        if (this.dragon == null) {
            this.dragon = new Dragon();
        } else {

            if (this.dragon.wakeup == null) {
                this.dragon.wakeup = new Dragon.Wakeup();
            } else {

                this.dragon.wakeup.vibrations = Math.max(0, Math.min(this.dragon.wakeup.vibrations, 10));

                this.dragon.wakeup.roar_radius = Math.max(0.0f, this.dragon.wakeup.roar_radius);

                this.dragon.wakeup.roar_knockback = Math.max(0.0f, this.dragon.wakeup.roar_knockback);

                this.dragon.wakeup.roar_destroy = Math.max(0, Math.min(this.dragon.wakeup.roar_destroy, 25));

                this.dragon.wakeup.roar_damage = Math.max(0.0f, this.dragon.wakeup.roar_damage);

            }

            if (this.dragon.age == null) {
                this.dragon.age = new Dragon.Age();
            } else {

                this.dragon.age.stage_ticks = Math.max(0, this.dragon.age.stage_ticks);

                this.dragon.age.min_breed_stage = Math.max(-1, Math.min(this.dragon.age.min_breed_stage, 4));

                this.dragon.age.min_mount_stage = Math.max(-1, Math.min(this.dragon.age.min_mount_stage, 4));

                this.dragon.age.min_xp_stage = Math.max(-1, Math.min(this.dragon.age.min_xp_stage, 4));

            }

        }

    }

}
