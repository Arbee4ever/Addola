package net.arbee.addola.client.render;

import net.arbee.addola.Addola;
import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.item.ChestBoatItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class ChestBoatItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static ChestBoatEntity DUMMY;

    @Override
    public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if ((DUMMY == null && MinecraftClient.getInstance().world != null) || MinecraftClient.getInstance().world != DUMMY.world) {
            DUMMY = new ChestBoatEntity(Addola.CHESTBOAT, MinecraftClient.getInstance().world);
        }
        /*
        DUMMY.setBoatType(((ChestBoatItem)stack.getItem()).getBoatType());
         */
        EntityRenderer<? super ChestBoatEntity> renderer = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(DUMMY);
        matrices.translate(0.5, 0, 0.5);
        renderer.render(DUMMY, DUMMY.getYaw(10), 0, matrices, vertexConsumers, light);
    }

}
