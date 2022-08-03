package net.totobirdcreations.dragonheart.gamerule;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;


public class Gamerules {


    public static final GameRules.Key<GameRules.BooleanRule> PLATED_DRAGON_FORGE_BRICKS_UNBREAKABLE = GameRuleRegistry.register(
            "platedDragonBricksUnbreakable",
            GameRules.Category.MISC,
            GameRuleFactory.createBooleanRule(false)
    );

    public static final GameRules.Key<GameRules.BooleanRule> DRAGON_GRIEFING_PERMANENT = GameRuleRegistry.register(
            "dragonGriefingPermanent",
            GameRules.Category.MISC,
            GameRuleFactory.createBooleanRule(false)
    );


    public static void register() {}


}
