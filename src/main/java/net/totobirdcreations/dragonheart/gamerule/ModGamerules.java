package net.totobirdcreations.dragonheart.gamerule;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGamerules {


    public static final GameRules.Key<GameRules.BooleanRule> DRAGONSTEELBLOCK_UNBREAKABLE = GameRuleRegistry.register(
            "dragonsteelBlockUnbreakable",
            GameRules.Category.MISC,
            GameRuleFactory.createBooleanRule(true)
    );


    public static void register() {}


}
