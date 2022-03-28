package net.arbee.addola.entity.vehicle;

import net.arbee.addola.Addola;
import net.arbee.addola.mixins.BoatEntityAccess;
import net.arbee.addola.network.SpawnChestBoatEntityPacketSender;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ChestBoatEntity extends BoatEntity {
    Block block;

    public ChestBoatEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public ChestBoatEntity(EntityType<? extends ChestBoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ChestBoatEntity instance = this;
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (((BoatEntityAccess)instance).getTicksUnderwater() < 60.0F) {
            if (!this.world.isClient) {
                block = Block.getBlockFromItem(player.getMainHandStack().getItem());
                if(block.hasBlockEntity()) {
                    player.getMainHandStack().decrement(1);
                }
                return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
            } else {
                return ActionResult.SUCCESS;
            }
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public Item asItem() {
        return Addola.CHESTBOAT_ITEM;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < 1 && !this.isSubmergedIn(FluidTags.WATER);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return SpawnChestBoatEntityPacketSender.createSpawnPacket(this);
    }
}
