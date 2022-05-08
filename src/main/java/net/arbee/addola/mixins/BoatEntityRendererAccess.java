package net.arbee.addola.mixins;

import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatEntityRenderer.class)
public interface BoatEntityRendererAccess {
    @Accessor
    static Identifier[] getTEXTURES() {
        throw new AssertionError();
    }
}
