package net.arbee.addola.entity.vehicle;

import net.arbee.addola.mixins.BoatEntityAccess;
import net.arbee.addola.mixins.EntityAccess;
import net.arbee.addola.network.SpawnChestBoatEntityPacketSender;
import net.arbee.addola.registries.AddolaEntities;
import net.arbee.addola.registries.AddolaItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ChestBoatEntity extends BoatEntity {
    private static final TrackedData<String> CONTAINER;


    static {
        CONTAINER = DataTracker.registerData(ChestBoatEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    public ChestBoatEntity(World world, double x, double y, double z) {
        super(AddolaEntities.CHESTBOAT, world);
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
        if (((BoatEntityAccess)this).getTicksUnderwater() < 60.0F) {
            if (!this.world.isClient) {
                Identifier item = Registry.ITEM.getId(player.getMainHandStack().getItem());
                if (player.isSneaking()) {
                    if (item.equals(Registry.ITEM.getId(Items.AIR))) {
                        world.spawnEntity(new ItemEntity(world, getX(), getY() + 0.6D, getZ(), new ItemStack(Registry.ITEM.get(getContainer()))));
                        BoatEntity boat = new BoatEntity(world, getX(), getY(), getZ());
                        boat.setBoatType(getBoatType());
                        world.spawnEntity(boat);
                        boat.copyPositionAndRotation(this);
                        boat.pitch = 60.0F;
                        remove();
                    } else if (Registry.BLOCK.get(Registry.ITEM.getId(player.getMainHandStack().getItem())).getDefaultState() instanceof Inventory) {
                        world.spawnEntity(new ItemEntity(world, getX(), getY() + 0.6D, getZ(), new ItemStack(Registry.ITEM.get(getContainer()))));
                        setContainer(Registry.ITEM.getId(player.getMainHandStack().getItem()));
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
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isClient && !this.removed) {
            this.setDamageWobbleSide(-this.getDamageWobbleSide());
            this.setDamageWobbleTicks(10);
            this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0F);
            this.scheduleVelocityUpdate();
            boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).abilities.creativeMode;
            if (bl || this.getDamageWobbleStrength() > 40.0F) {
                if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    ItemStack itemStack = new ItemStack(this.asItem());
                    CompoundTag compoundTag = new CompoundTag();
                    compoundTag.putString("Container", this.getContainer().toString());
                    if (!compoundTag.isEmpty()) {
                        itemStack.putSubTag("EntityTag", compoundTag);
                    }
                    ItemEntity itemEntity = new ItemEntity(world, ((EntityAccess)this).getPos().getX() + 0.5D, ((EntityAccess)this).getPos().getY() + 0.5D, ((EntityAccess)this).getPos().getZ() + 0.5D, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }

                this.remove();
            }

            return true;
        } else {
            return true;
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CONTAINER, Registry.BLOCK.getId(Blocks.CHEST).toString());
    }

    protected void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putString("Container", getContainer().toString());
    }

    protected void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("Container", 8)) {
            this.setContainer(new Identifier(tag.getString("Container")));
        }
    }

    public void setContainer(Identifier container) {
        this.dataTracker.set(CONTAINER, container.toString());
    }

    public Identifier getContainer() {
        return new Identifier(this.dataTracker.get(CONTAINER));
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            float f = 0.2F;
            float g = (float)((this.removed ? 0.009999999776482582D : this.getMountedHeightOffset()) + passenger.getHeightOffset());

            if (passenger instanceof AnimalEntity) {
                f = (float)((double)f + 0.2D);
            }

            Vec3d vec3d = (new Vec3d(f, 0.0D, 0.0D)).rotateY(-this.yaw * 0.017453292F - 1.5707964F);
            passenger.updatePosition(this.getX() + vec3d.x, this.getY() + (double)g, this.getZ() + vec3d.z);
            passenger.yaw += ((BoatEntityAccess)this).getYawVelocity();
            passenger.setHeadYaw(passenger.getHeadYaw() + ((BoatEntityAccess)this).getYawVelocity());
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
        switch(this.getBoatType()) {
            case OAK:
            default:
                return AddolaItems.OAK_CHESTBOAT_ITEM;
            case SPRUCE:
                return AddolaItems.SPRUCE_CHESTBOAT_ITEM;
            case BIRCH:
                return AddolaItems.BIRCH_CHESTBOAT_ITEM;
            case JUNGLE:
                return AddolaItems.JUNGLE_CHESTBOAT_ITEM;
            case ACACIA:
                return AddolaItems.ACACIA_CHESTBOAT_ITEM;
            case DARK_OAK:
                return AddolaItems.DARKOAK_CHESTBOAT_ITEM;
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < 1 && !this.isSubmergedIn(FluidTags.WATER);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return SpawnChestBoatEntityPacketSender.create(this);
    }
}
