package net.arbee.addola.mixins;

import net.arbee.addola.registries.AddolaGamerules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Shadow @Final private List<ServerPlayerEntity> players;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setTimeOfDay(J)V"), method = "tick")
    public void inject(CallbackInfo ci) {
        for (int i = 0; i < players.size(); i++) {
            LivingEntity player = players.get(i);
            int gameruleInt = player.getEntityWorld().getGameRules().getInt(AddolaGamerules.HEAL_ON_SLEEP);

            player.setHealth(Math.min(player.getHealth() + gameruleInt, player.getMaxHealth()));

            if(player.getEntityWorld().getGameRules().getBoolean(AddolaGamerules.CURE_EFFECTS_SLEEP)) {
                player.clearStatusEffects();
            }
        }
    }
}
