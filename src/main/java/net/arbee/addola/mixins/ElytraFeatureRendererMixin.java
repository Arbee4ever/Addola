package net.arbee.addola.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	
    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
		if (itemStack.getItem() == Items.ELYTRA) {
			Identifier identifier4;
			AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)livingEntity;
			if("guendahr".equals(abstractClientPlayerEntity.getName().getString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
				ci.cancel();
			}
		}
    }
}
