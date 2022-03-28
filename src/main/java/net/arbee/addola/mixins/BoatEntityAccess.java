package net.arbee.addola.mixins;

import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatEntity.class)
public interface BoatEntityAccess {
    @Accessor float getTicksUnderwater();
    @Accessor float getYawVelocity();
}
