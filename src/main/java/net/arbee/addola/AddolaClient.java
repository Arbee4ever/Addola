package net.arbee.addola;

import io.netty.buffer.Unpooled;
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
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.MinecraftVersion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class AddolaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AddolaItems.setupItemRenderers();

        EntityRendererRegistry.INSTANCE.register(AddolaEntities.CHESTBOAT, (dispatcher, context) -> new ChestBoatEntityRenderer(dispatcher));

        receiveEntityPacket();
    }

    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(SpawnChestBoatEntityPacketSender.IDENTIFIER, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = SpawnChestBoatEntityPacketSender.PacketBufUtil.readVec3d(byteBuf);
            float pitch = SpawnChestBoatEntityPacketSender.PacketBufUtil.readAngle(byteBuf);
            float yaw = SpawnChestBoatEntityPacketSender.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.pitch = pitch;
                e.yaw = yaw;
                e.setEntityId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}
