package net.arbee.addola.client.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class AddolaElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraFeatureRenderer<T, M> {
	private static final Identifier SKIN = new Identifier("textures/entity/elytra.png");
	public final String[] friends = AddolaCapeFeatureRender.friends;
	private final ElytraEntityModel<T> elytra;

	public AddolaElytraFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext, EntityModelLoader loader) {
		super(featureRendererContext, loader);
		elytra = new ElytraEntityModel<>(loader.getModelPart(EntityModelLayers.ELYTRA));
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
		AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity) livingEntity;
		if (!abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
			ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
			if (itemStack.getItem() == Items.ELYTRA) {
				Identifier identifier4 = SKIN;
				if (livingEntity instanceof AbstractClientPlayerEntity) {
					if ("51625729-02bb-4125-a45f-332520b04f19".equals(livingEntity.getUuid().toString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
						identifier4 = new Identifier("addola:" + "capes/arbeecape.png");
						renderIt(matrixStack, vertexConsumerProvider, i, livingEntity, f, g, h, j, k, l, identifier4, itemStack);
					}
					for (i = 0; i < friends.length; i++) {
						if (friends[i].equals(livingEntity.getUuid().toString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
							identifier4 = new Identifier("addola:" + "capes/friendscape.png");
							renderIt(matrixStack, vertexConsumerProvider, i, livingEntity, f, g, h, j, k, l, identifier4, itemStack);
						}
					}
				}
			}
		}
	}

	public void renderIt(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, Identifier identifier4, ItemStack itemStack) {
		matrixStack.push();
		matrixStack.translate(0.0D, 0.0D, 0.125D);
		this.getContextModel().copyStateTo(this.elytra);
		this.elytra.setAngles(livingEntity, f, g, j, k, l);
		VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier4), false, itemStack.hasGlint());
		this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
	}
}