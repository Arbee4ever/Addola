package net.arbee.addola;

import net.arbee.addola.client.render.BoatItemRenderer;
import net.arbee.addola.client.render.ChestBoatEntityRenderer;
import net.arbee.addola.client.render.ChestBoatItemRenderer;
import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.network.SpawnChestBoatEntityPacketSender;
import net.arbee.addola.registries.AddolaEntities;
import net.arbee.addola.registries.AddolaItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.MinecraftVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class AddolaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SpawnChestBoatEntityPacketSender.IDENTIFIER, (client, handler, buf, responseSender) -> {
            final int entityId = buf.readInt();
            final UUID uuid = buf.readUuid();
            final double x = buf.readDouble();
            final double y = buf.readDouble();
            final double z = buf.readDouble();
            final double xVelocity = buf.readDouble();
            final double yVelocity = buf.readDouble();
            final double zVelocity = buf.readDouble();
            final float pitch = buf.readFloat();
            final float yaw = buf.readFloat();
            final ChestBoatEntity boat = new ChestBoatEntity(AddolaEntities.CHESTBOAT, client.world);
            boat.setEntityId(entityId);
            boat.setUuid(uuid);
            boat.setPos(x, y, z);
            boat.setVelocity(xVelocity, yVelocity, zVelocity);
            boat.setYaw(yaw);
            boat.pitch = pitch;
            client.execute(() -> client.world.addEntity(entityId, boat));
        });

        BuiltinItemRendererRegistry.INSTANCE.register(Items.ACACIA_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.OAK_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.BIRCH_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.DARK_OAK_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.JUNGLE_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.SPRUCE_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(AddolaItems.OAK_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(AddolaItems.SPRUCE_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(AddolaItems.BIRCH_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(AddolaItems.JUNGLE_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(AddolaItems.ACACIA_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(AddolaItems.DARKOAK_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        EntityRendererRegistry.INSTANCE.register(AddolaEntities.CHESTBOAT, (dispatcher, context) -> new ChestBoatEntityRenderer(dispatcher));
    }
}
