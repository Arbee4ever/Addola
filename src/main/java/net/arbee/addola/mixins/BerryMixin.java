package net.arbee.addola.mixins;

import net.arbee.addola.registries.Gamerules;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SweetBerryBushBlock.class)
public class BerryMixin{
    @Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    public boolean collision (Entity entity, DamageSource source, float f) {
        if(entity.isInSneakingPose() && !entity.getEntityWorld().getGameRules().getBoolean(Gamerules.BERRYBUSH_SNEAK_DAMAGE)) {
            entity.damage(source, 0);
        } else {
            entity.damage(source, f);
        }
        return true;
    }
}
