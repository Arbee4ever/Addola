package net.arbee.addola.mixins;

import net.arbee.addola.client.render.AddolaCapeFeatureRender;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private final String[] capeowners = AddolaCapeFeatureRender.friends;

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		if (livingEntity instanceof AbstractClientPlayerEntity) {
			ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
			if (itemStack.getItem() == Items.ELYTRA) {
				AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity) livingEntity;
				if ("51625729-02bb-4125-a45f-332520b04f19".equals(abstractClientPlayerEntity.getUuid().toString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
					ci.cancel();
				}
				for (i=0; i<capeowners.length; i++) {
					if (capeowners[i].equals(abstractClientPlayerEntity.getUuid().toString()) && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
						ci.cancel();
					}
				}
			}
		}
    }
}
