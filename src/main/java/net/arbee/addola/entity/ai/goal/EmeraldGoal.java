package net.arbee.addola.entity.ai.goal;

import net.arbee.addola.registries.Gamerules;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.recipe.Ingredient;

public class EmeraldGoal extends TemptGoal {
    private static final TargetPredicate TEMPTING_ENTITY_PREDICATE = (new TargetPredicate()).setBaseMaxDistance(10.0D).includeInvulnerable().includeTeammates().ignoreEntityTargetRules().includeHidden();
    private int cooldown;

    public EmeraldGoal(PathAwareEntity mob, double speed, Ingredient food, boolean canBeScared) {
        super(mob, speed, food, canBeScared);
    }

    @Override
    public boolean canStart() {
        if (this.mob.world.getGameRules().getBoolean(Gamerules.VILLAGERS_FOLLOW)) {
            if (this.cooldown > 0) {
                --this.cooldown;
                return false;
            } else {
                this.closestPlayer = this.mob.world.getClosestPlayer(TEMPTING_ENTITY_PREDICATE, this.mob);
                if (this.closestPlayer == null) {
                    return false;
                } else {
                    return this.isTemptedBy(this.closestPlayer.getMainHandStack()) || this.isTemptedBy(this.closestPlayer.getOffHandStack());
                }
            }
        } else {
            return false;
        }
    }
}
