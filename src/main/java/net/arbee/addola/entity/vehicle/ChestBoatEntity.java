package net.arbee.addola.entity.vehicle;

import net.arbee.addola.Addola;
import net.arbee.addola.mixins.BoatEntityAccess;
import net.arbee.addola.network.SpawnChestBoatEntityPacketSender;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChestBoatEntity extends BoatEntity {
    private static final TrackedData<String> BLOCK_ENTITY;
    ChestBoatEntity instance = this;


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

    public ChestBoatEntity(EntityType<? extends ChestBoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (((BoatEntityAccess)instance).getTicksUnderwater() < 60.0F) {
            if (!this.world.isClient) {
                Block block = Registry.BLOCK.get(Registry.ITEM.getId(player.getMainHandStack().getItem()));
                if (player.isSneaking()) {
                    if (block.equals(Blocks.AIR)) {
                        world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(Registry.ITEM.get(new Identifier(getBlockEntity())))));
                        BoatEntity boat = new BoatEntity(world, getX(), getY(), getZ());
                        world.spawnEntity(boat);
                        boat.copyPositionAndRotation(this);
                        remove();
                    } else if (block.hasBlockEntity()) {
                        world.spawnEntity(new ItemEntity(world, getX(), getY(), getZ(), new ItemStack(Registry.ITEM.get(new Identifier(getBlockEntity())))));
                        setBlockEntity(Registry.ITEM.getId(player.getMainHandStack().getItem()).toString());
                        player.getMainHandStack().decrement(1);
                        return ActionResult.SUCCESS;
                    }
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
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BLOCK_ENTITY, Registry.BLOCK.getId(Blocks.CHEST).toString());
    }

    protected void writeCustomDataToTag(CompoundTag tag) {
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
        return Addola.OAK_CHESTBOAT_ITEM;
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
