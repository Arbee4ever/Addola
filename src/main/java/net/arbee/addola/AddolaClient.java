package net.arbee.addola;

import net.arbee.addola.entity.renderer.ChestBoatEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class AddolaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(Addola.CHESTBOAT, (dispatcher, context) -> {
            return new ChestBoatEntityRenderer(dispatcher);
        });

    }
}
