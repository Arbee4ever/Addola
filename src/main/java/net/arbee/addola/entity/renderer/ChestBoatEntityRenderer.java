package net.arbee.addola.entity.renderer;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

import java.rmi.registry.Registry;

public class ChestBoatEntityRenderer extends EntityRenderer<ChestBoatEntity> {
    protected final BoatEntityModel model = new BoatEntityModel();
    private static final Identifier[] TEXTURES = new Identifier[]{new Identifier("textures/entity/boat/oak.png"), new Identifier("textures/entity/boat/spruce.png"), new Identifier("textures/entity/boat/birch.png"), new Identifier("textures/entity/boat/jungle.png"), new Identifier("textures/entity/boat/acacia.png"), new Identifier("textures/entity/boat/dark_oak.png")};

    public ChestBoatEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.8F;
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
        this.model.setAngles(chestBoatEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(chestBoatEntity)));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!chestBoatEntity.isSubmergedInWater()) {
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
            this.model.getBottom().render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV);
        }
        matrixStack.scale(-0.9F, -0.9F, 0.9F);
        matrixStack.translate(-0.1D, -0.15D, -0.5D);

        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.CHEST.getDefaultState(), matrixStack, vertexConsumerProvider, i, 0);

        matrixStack.pop();
        super.render(chestBoatEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(ChestBoatEntity chestBoatEntity) {
        return TEXTURES[chestBoatEntity.getBoatType().ordinal()];
    }

    protected Block readCustomDataFromTag(CompoundTag tag) {
        if (tag.contains("Block", 8)) {
            tag.getString("Block");
            return Blocks.CHEST;
        } else {
            return Blocks.CHEST;
        }
    }
}