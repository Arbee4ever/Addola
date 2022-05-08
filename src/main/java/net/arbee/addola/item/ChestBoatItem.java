package net.arbee.addola.item;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.mixins.BoatItemAccess;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ChestBoatItem extends BoatItem {
    private Identifier container;

    public ChestBoatItem(ChestBoatEntity.Type type, Item.Settings settings) {
        super(type, settings);
        setContainer(Registry.BLOCK.getId(Blocks.CHEST));
        settings.maxCount(1);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else {
            Vec3d vec3d = user.getRotationVec(1.0F);
            double d = 5.0D;
            List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), ((BoatItemAccess)this).getRIDERS());
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getCameraPosVec(1.0F);
                Iterator var11 = list.iterator();

                while(var11.hasNext()) {
                    Entity entity = (Entity)var11.next();
                    Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                    if (box.contains(vec3d2)) {
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                ChestBoatEntity chestBoatEntity = new ChestBoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
                chestBoatEntity.setBoatType(((BoatItemAccess)this).getType());
                if (itemStack.hasTag()) {
                    chestBoatEntity.setContainer(new Identifier(itemStack.getSubTag("EntityTag").getString("Container")));
                }
                chestBoatEntity.yaw = user.yaw;
                if (!world.isSpaceEmpty(chestBoatEntity, chestBoatEntity.getBoundingBox().expand(-0.1D))) {
                    return TypedActionResult.fail(itemStack);
                } else {
                    if (!world.isClient) {
                        world.spawnEntity(chestBoatEntity);
                        if (!user.abilities.creativeMode) {
                            itemStack.decrement(1);
                        }
                    }

                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.success(itemStack, world.isClient());
                }
            } else {
                return TypedActionResult.pass(itemStack);
            }
        }
    }

    public BoatEntity.Type getBoatType() {
        return ((BoatItemAccess)this).getType();
    }

    public void setContainer(Identifier container) {
        this.container = container;
    }

    public Identifier getContainer() {
        return container;
    }
}
