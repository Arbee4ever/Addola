package net.arbee.addola.client.render;

import net.arbee.addola.mixins.BoatItemAccess;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;


public class BoatItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static BoatEntity DUMMY;

    @Override
    public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if ((DUMMY == null && MinecraftClient.getInstance().world != null) || MinecraftClient.getInstance().world != DUMMY.world) {
            DUMMY = new BoatEntity(EntityType.BOAT, MinecraftClient.getInstance().world);
        }
        DUMMY.setBoatType(((BoatItemAccess)stack.getItem()).getType());
        EntityRenderer<? super BoatEntity> renderer = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(DUMMY);
        matrices.translate(0.5, 0, 0.5);
        renderer.render(DUMMY, DUMMY.getYaw(10), 0, matrices, vertexConsumers, light);
    }

}
