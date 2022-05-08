package net.arbee.addola;

import net.arbee.addola.entity.renderer.ChestBoatEntityRenderer;
import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.network.SpawnChestBoatEntityPacketSender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

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
            final ChestBoatEntity boat = new ChestBoatEntity(Addola.CHESTBOAT, client.world);
            boat.setEntityId(entityId);
            boat.setUuid(uuid);
            boat.setPos(x, y, z);
            boat.setVelocity(xVelocity, yVelocity, zVelocity);
            boat.setYaw(yaw);
            boat.pitch = pitch;
            client.execute(() -> client.world.addEntity(entityId, boat));
        });

        EntityRendererRegistry.INSTANCE.register(Addola.CHESTBOAT, (dispatcher, context) -> new ChestBoatEntityRenderer(dispatcher));
    }
}
