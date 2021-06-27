package net.arbee.addola.mixins;

import net.arbee.addola.ReferenceClient;
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
        if(ReferenceClient.config.healOnSleepAmount > 0) {
            if(((LivingEntity) (Object) this).getHealth() + ReferenceClient.config.healOnSleepAmount > ((LivingEntity) (Object) this).getMaxHealth()){
                ((LivingEntity) (Object) this).setHealth(((LivingEntity) (Object) this).getMaxHealth());
            } else {
                ((LivingEntity) (Object) this).setHealth(((LivingEntity) (Object) this).getHealth() + ReferenceClient.config.healOnSleepAmount);
            }
        }
        if(ReferenceClient.config.cureOnSleep == true) {
            ((LivingEntity) (Object) this).clearStatusEffects();
        }
    }
}
