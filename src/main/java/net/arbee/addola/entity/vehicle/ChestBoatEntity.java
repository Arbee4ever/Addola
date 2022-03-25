package net.arbee.addola.entity.vehicle;

import net.arbee.addola.mixins.BoatEntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ChestBoatEntity extends BoatEntity {

    public ChestBoatEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public ChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public ActionResult interact(PlayerEntity player, Hand hand, BoatEntity instance) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (((BoatEntityAccess)instance).getTicksUnderwater() < 60.0F) {
            if (!this.world.isClient) {
                return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
            } else {
                return ActionResult.SUCCESS;
            }
        } else {
            return ActionResult.PASS;
        }
    }
}
