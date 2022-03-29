package net.arbee.addola.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;

@Mixin(BoatItem.class)
public interface BoatItemAccess {
    @Accessor BoatEntity.Type getType();
    @Accessor Predicate<Entity> getRIDERS();
}
