package net.arbee.addola.entity.vehicle;

import net.arbee.addola.Addola;
import net.arbee.addola.mixins.BoatEntityAccess;
import net.arbee.addola.network.SpawnChestBoatEntityPacketSender;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChestBoatEntity extends BoatEntity {
    private static final TrackedData<String> BLOCK_ENTITY;
    ChestBoatEntity instance = this;
    Block block;


    static {
        BLOCK_ENTITY = DataTracker.registerData(ChestBoatEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    public ChestBoatEntity(World world, double x, double y, double z) {
        super(Addola.CHESTBOAT, world);
        updatePosition(x, y, z);
        setVelocity(Vec3d.ZERO);
        prevX = x;
        prevY = y;
        prevZ = z;
    }

    public ChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (((BoatEntityAccess)instance).getTicksUnderwater() < 60.0F) {
            if (!this.world.isClient) {
                /*block = Block.getBlockFromItem(player.getMainHandStack().getItem());
                if(block.hasBlockEntity()) {
                    player.getMainHandStack().decrement(1);
                }*/
                if (player.isSneaking()) {
                    new BoatEntity(this.world, this.getX(), this.getY(), this.getZ());
                    return ActionResult.SUCCESS;
                }
                return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
            } else {
                return ActionResult.SUCCESS;
            }
        } else {
            return ActionResult.PASS;
        }
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_ENTITY, Blocks.CHEST.getName().getString());
    }

    protected void writeCustomDataToTag(CompoundTag tag) {
        System.out.print(getBlockEntity());
        tag.putString("BlockEntity", getBlockEntity());
    }

    protected void readCustomDataFromTag(CompoundTag tag) {
        if (tag.contains("BlockEntity", 8)) {
            this.setBlockEntity(tag.getString("BlockEntity"));
        }
    }

    public void setBlockEntity(String blockEntity) {
        this.dataTracker.set(BLOCK_ENTITY, blockEntity);
    }

    public String getBlockEntity() {
        return this.dataTracker.get(BLOCK_ENTITY);
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            float f = 0.2F;
            float g = (float)((this.removed ? 0.009999999776482582D : this.getMountedHeightOffset()) + passenger.getHeightOffset());

            if (passenger instanceof AnimalEntity) {
                f = (float)((double)f + 0.2D);
            }

            Vec3d vec3d = (new Vec3d((double)f, 0.0D, 0.0D)).rotateY(-this.yaw * 0.017453292F - 1.5707964F);
            passenger.updatePosition(this.getX() + vec3d.x, this.getY() + (double)g, this.getZ() + vec3d.z);
            passenger.yaw += ((BoatEntityAccess)instance).getYawVelocity();
            passenger.setHeadYaw(passenger.getHeadYaw() + ((BoatEntityAccess)instance).getYawVelocity());
            this.copyEntityData(passenger);
            if (passenger instanceof AnimalEntity && this.getPassengerList().size() > 1) {
                int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                passenger.setYaw(((AnimalEntity)passenger).bodyYaw + (float)j);
                passenger.setHeadYaw(passenger.getHeadYaw() + (float)j);
            }
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
