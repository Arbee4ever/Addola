package net.arbee.addola.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
public class AddolaCapeFeatureRender extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	public static Identifier identifier4;
	public static final String[] friends = new String[] {
			"cf58bcae-5731-4c11-86ff-a84f32f168e4", //fieteeee
			"cf428a70-8447-46b5-b3fd-625b31a72842", //hggfred
			"368e579a-166f-4f41-88b0-5bfcaffa9472" //eeevelyn
	};

	public AddolaCapeFeatureRender(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
		super(featureRendererContext);
	}
	
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
		ItemStack itemStack = abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.CHEST);
		if (itemStack.getItem() != Items.ELYTRA) {
			if (abstractClientPlayerEntity instanceof AbstractClientPlayerEntity) {
				if ("51625729-02bb-4125-a45f-332520b04f19".equals(abstractClientPlayerEntity.getUuid().toString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE) && !abstractClientPlayerEntity.isInvisible()) {
					identifier4 = new Identifier("addola:" + "capes/arbeecape.png");
					renderIt(matrixStack, vertexConsumerProvider, i, abstractClientPlayerEntity, f, g, h, j, k, l, identifier4, itemStack);
				}
				for (i = 0; i < friends.length; i++) {
					if (friends[i].equals(abstractClientPlayerEntity.getUuid().toString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE) && !abstractClientPlayerEntity.isInvisible()) {
						identifier4 = new Identifier("addola:" + "capes/friendscape.png");
						renderIt(matrixStack, vertexConsumerProvider, i, abstractClientPlayerEntity, f, g, h, j, k, l, identifier4, itemStack);
					}
				}
			}
		}
	}

	public void renderIt(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l, Identifier identifier4, ItemStack itemStack) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.0D, 0.125D);
		double d = MathHelper.lerp(h, abstractClientPlayerEntity.prevCapeX, abstractClientPlayerEntity.capeX) - MathHelper.lerp(h, abstractClientPlayerEntity.prevX, abstractClientPlayerEntity.getX());
		double e = MathHelper.lerp(h, abstractClientPlayerEntity.prevCapeY, abstractClientPlayerEntity.capeY) - MathHelper.lerp(h, abstractClientPlayerEntity.prevY, abstractClientPlayerEntity.getY());
		double m = MathHelper.lerp(h, abstractClientPlayerEntity.prevCapeZ, abstractClientPlayerEntity.capeZ) - MathHelper.lerp(h, abstractClientPlayerEntity.prevZ, abstractClientPlayerEntity.getZ());
		float n = abstractClientPlayerEntity.prevBodyYaw + (abstractClientPlayerEntity.bodyYaw - abstractClientPlayerEntity.prevBodyYaw);
		double o = MathHelper.sin(n * 0.017453292F);
		double p = (-MathHelper.cos(n * 0.017453292F));
		float q = (float) e * 10.0F;
		q = MathHelper.clamp(q, -6.0F, 32.0F);
		float r = (float) (d * o + m * p) * 100.0F;
		r = MathHelper.clamp(r, 0.0F, 150.0F);
		float s = (float) (d * p - m * o) * 100.0F;
		s = MathHelper.clamp(s, -20.0F, 20.0F);
		if (r < 0.0F) {
			r = 0.0F;
		}

		float t = MathHelper.lerp(h, abstractClientPlayerEntity.prevStrideDistance, abstractClientPlayerEntity.strideDistance);
		q += MathHelper.sin(MathHelper.lerp(h, abstractClientPlayerEntity.prevHorizontalSpeed, abstractClientPlayerEntity.horizontalSpeed) * 6.0F) * 32.0F * t;
		if (abstractClientPlayerEntity.isInSneakingPose()) {
			q += 25.0F;
		}

		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(6.0F + r / 2.0F + q));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(s / 2.0F));
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - s / 2.0F));
		VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier4), false , itemStack.hasGlint());
		this.getContextModel().renderCape(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		matrixStack.pop();
	}
}