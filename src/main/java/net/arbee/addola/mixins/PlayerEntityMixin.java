package net.arbee.addola.mixins;

import net.arbee.addola.Gamerules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "wakeUp(ZZ)V")
    public void wakeUp(CallbackInfo ci) {
        LivingEntity player = ((LivingEntity) (Object) this);
        int gameruleInt = player.getEntityWorld().getGameRules().getInt(Gamerules.HEAL_ON_SLEEP);

        player.setHealth(Math.min(player.getHealth() + gameruleInt, player.getMaxHealth()));

        if(player.getEntityWorld().getGameRules().getBoolean(Gamerules.CURE_EFFECTS_SLEEP)) {
            player.clearStatusEffects();
        }
    }
}
