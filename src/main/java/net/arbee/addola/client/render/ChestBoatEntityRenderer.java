package net.arbee.addola.client.render;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.mixins.BoatEntityRendererAccess;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.registry.Registry;

public class ChestBoatEntityRenderer extends EntityRenderer<ChestBoatEntity> {
    protected final BoatEntityModel model = new BoatEntityModel();

    public ChestBoatEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        shadowRadius = 0.8F;
    }

    public void render(ChestBoatEntity chestBoatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0D, 0.375D, 0.0D);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - f));
        float h = (float)chestBoatEntity.getDamageWobbleTicks() - g;
        float j = chestBoatEntity.getDamageWobbleStrength() - g;
        if (j < 0.0F) {
            j = 0.0F;
        }

        if (h > 0.0F) {
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0F * (float)chestBoatEntity.getDamageWobbleSide()));
        }

        float k = chestBoatEntity.interpolateBubbleWobble(g);
        if (!MathHelper.approximatelyEquals(k, 0.0F)) {
            matrixStack.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), chestBoatEntity.interpolateBubbleWobble(g), true));
        }

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        model.setAngles(chestBoatEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(model.getLayer(getTexture(chestBoatEntity)));
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!chestBoatEntity.isSubmergedInWater()) {
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
            model.getBottom().render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV);
        }
        matrixStack.scale(-0.8F, -0.8F, 0.8F);
        matrixStack.translate(-0.0D, -0.225D, -0.5D);

        BlockState container = Registry.BLOCK.get(chestBoatEntity.getContainer()).getDefaultState();

        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(container, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);

        matrixStack.pop();
        super.render(chestBoatEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(ChestBoatEntity chestBoatEntity) {
        return BoatEntityRendererAccess.getTEXTURES()[chestBoatEntity.getBoatType().ordinal()];
    }
}