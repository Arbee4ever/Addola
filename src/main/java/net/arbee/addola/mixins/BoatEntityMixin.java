package net.arbee.addola.mixins;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public class BoatEntityMixin {
    @Inject(at = @At("HEAD"), method = "interact", cancellable = true)
    private void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        Block block = Registry.BLOCK.get(Registry.ITEM.getId(player.getMainHandStack().getItem()));
        BoatEntity instance = (BoatEntity)(Object)this;
        if (player.isSneaking()) {
            if (block.hasBlockEntity()) {
                World world = player.getEntityWorld();
                ChestBoatEntity chestBoat = new ChestBoatEntity(world, instance.getX(), instance.getY(), instance.getZ());
                world.spawnEntity(chestBoat);
                chestBoat.copyPositionAndRotation(instance);
                chestBoat.setBlockEntity(Registry.ITEM.getId(player.getMainHandStack().getItem()).toString());
                player.getMainHandStack().decrement(1);
                instance.remove();
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
