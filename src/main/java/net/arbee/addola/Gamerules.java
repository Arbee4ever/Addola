package net.arbee.addola;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class Gamerules {
    public static GameRules.Key<GameRules.BooleanRule> BERRYBUSH_SNEAK_DAMAGE;
    public static GameRules.Key<GameRules.BooleanRule> VILLAGERS_FOLLOW;
    public static GameRules.Key<GameRules.IntRule> HEAL_ON_SLEEP;
    public static GameRules.Key<GameRules.BooleanRule> CURE_EFFECTS_SLEEP;

    public static void setupGamerules() {
        BERRYBUSH_SNEAK_DAMAGE = GameRuleRegistry.register("berryBushSneakDamage", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));
        VILLAGERS_FOLLOW = GameRuleRegistry.register("villagersFollow", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
        HEAL_ON_SLEEP = GameRuleRegistry.register("healOnSleep", GameRules.Category.MOBS, GameRuleFactory.createIntRule(20));
        CURE_EFFECTS_SLEEP = GameRuleRegistry.register("cureEffectsOnSleep", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
    }
}
